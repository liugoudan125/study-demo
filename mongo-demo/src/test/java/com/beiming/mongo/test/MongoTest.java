package com.beiming.mongo.test;

import com.beiming.mongo.Application;
import com.beiming.mongo.model.Car;
import com.beiming.mongo.model.Person;
import com.beiming.mongo.model.Result;
import com.beiming.mongo.repository.PersonRepository;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MongoTest
 */
@SpringBootTest(classes = Application.class)
public class MongoTest {

    @Resource
    private PersonRepository personRepository;

    @Resource
    private MongoTemplate mongoTemplate;


    @Test
    public void testInsert() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        AtomicInteger counter = new AtomicInteger(0);
        SynchronousQueue<List<Person>> queue = new SynchronousQueue<>();
        CountDownLatch countDownLatch = new CountDownLatch(3000);
        threadPool.execute(() -> {
            try {
                while (true) {
                    List<Person> take = queue.take();
                    personRepository.insert(take);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        for (int a = 0; a < 3000; a++) {
            List<Person> list = new ArrayList<>(100000);
            for (int i = 0; i < 100000; i++) {
                list.add(getPerson());
            }
            queue.put(list);

            System.out.println(counter.incrementAndGet());
            countDownLatch.countDown();
        }
        countDownLatch.await();
    }

    @Test
    public void testFind() {
        Criteria criteria = Criteria.where("cars.color").is("red");
        List<Person> personList = mongoTemplate.find(Query.query(criteria).limit(10), Person.class);
        personList.forEach(person -> {
            ObjectId objectId = new ObjectId(person.get_id());
            System.out.println(objectId.getDate());
        });
    }

    ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @Test
    public void testUpdate() throws InterruptedException {
//        Query query = Query.query(Criteria.where("cars.name").is("XBkzH"));

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.execute(() -> {
                Query query = Query.query(Criteria.where("cars.0.color").exists(false));
//                Person person = mongoTemplate.findOne(query, Person.class);
                Update update = new Update();
                update.set("cars.0.color", "dddss");
                System.out.println(finalI + ": " + mongoTemplate.updateMulti(query, update, Person.class).getModifiedCount());
            });
        }
        Thread.sleep(1000 * 3L);

    }

    @Test
    public void testAggregate() {
        GroupOperation ageGroup = Aggregation.group("age").count().as("ageCount");
        MatchOperation whereOperation = Aggregation.match(Criteria.where("cars.color").is("red"));
        Aggregation aggregation = Aggregation.newAggregation(ageGroup, whereOperation);
        AggregationResults<HashMap> results = mongoTemplate.aggregate(aggregation, "person", HashMap.class);
        for (HashMap mappedResult : results.getMappedResults()) {
            System.out.println(mappedResult);
        }


    }

    static long id = 500000000;
    static String[] colors = new String[]{"red", "blue", "black", "gray", "yellow", "pink"};
    static Random random = new Random();

    private static Person getPerson() {
        Person person = new Person();
        person.setId(id++);
        person.setName(RandomStringUtils.randomAlphabetic(10, 15));
        int age = ThreadLocalRandom.current().nextInt(18, 70);
        person.setAge(age);
        List<Car> cars = new ArrayList<>();
        int carNum = random.nextInt(1, 5);
        for (int i = 0; i < carNum; i++) {
            cars.add(getCar());
        }
        person.setCars(cars);
        return person;
    }

    private static Car getCar() {
        Car car1 = new Car();
        car1.setName(RandomStringUtils.randomAlphabetic(4, 10));
        car1.setColor(colors[random.nextInt(colors.length)]);
        car1.setPrice(ThreadLocalRandom.current().nextLong(100000, 500000));
        return car1;
    }

    @Test
    public void testFindPage() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Page<Person> page = personRepository.findByIdGreaterThan(0L, PageRequest.of(0, 10));
        System.out.println(page.getTotalPages());

    }

    @Test
    public void testSpeed() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        int pageNum = 0;
        //用来做滑动分页,防止深分页
        Long lastId;
        //是否是第一开始分页查询
        boolean isFirst = true;
        List<Person> list = new ArrayList<>();
        Map<Result.ID, Result> map = new HashMap<>();

        long start = System.currentTimeMillis();
        while (ObjectUtils.isNotEmpty(list) || isFirst) {
            if (isFirst) {
                isFirst = false;
            }
            if (ObjectUtils.isNotEmpty(list)) {
                Person person = list.getLast();
                lastId = person.getId();
            } else {
                lastId = 0L;
            }
            list = listByIdGreaterThan(lastId);
            System.out.println(++pageNum);
            List<Person> finalList = list;
            threadPool.execute(() -> {
                if (ObjectUtils.isNotEmpty(finalList)) {
                    for (Person person : finalList) {
                        Result.ID id = new Result.ID();
                        id.setAge(person.getAge());
                        Result result1 = map.computeIfAbsent(id, k -> {
                            Result result = new Result();
                            result.setId(k);
                            return result;
                        });
                        result1.setCount(person.getCars().size());
                        for (Car car : person.getCars()) {
                            if ("red".equals(car.getColor())) {
                                result1.setIsRed(result1.getIsRed() + 1);
                            } else {
                                result1.setIsNotRed(result1.getIsNotRed() + 1);
                            }
                            result1.setTotalPrice(result1.getTotalPrice() + car.getPrice());
                        }
                    }
                }
            });
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            Thread.sleep(10000L);
        }

        for (Result value : map.values()) {
            System.out.println(value);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    private List<Person> listByIdGreaterThan(long id) {
        return mongoTemplate.find(Query.query(Criteria.where("id").gt(id)).limit(100000), Person.class);
    }

    @Test
    public void testSpeed1() {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("id").gte(1000000000).lt(1000000005));
        UnwindOperation unwind = Aggregation.unwind("cars");
        GroupOperation groupOperation = Aggregation
                .group("age", "cars.color")
                .sum(ConditionalOperators.when(Criteria.where("cars.color").is("red"))
                        .then(1).otherwise(0)
                ).as("isRed")
                .sum(ConditionalOperators.when(Criteria.where("cars.color").ne("red")).then(1).otherwise(0))
                .as("isNotRed")
                .sum("cars.price").as("totalPrice");

        mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, Aggregation.group("age")), Person.class, Result.class);
        AggregationResults<Result> aggregate = mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation, unwind, groupOperation), Person.class, Result.class);
        List<Result> mappedResults = aggregate.getMappedResults();
        for (Result mappedResult : mappedResults) {
            System.out.println(mappedResult);
        }
    }

}
