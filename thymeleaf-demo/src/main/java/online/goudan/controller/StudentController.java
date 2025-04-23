package online.goudan.controller;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import online.goudan.domain.Student;
import online.goudan.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author goudan
 * @date 2023/8/2 13:47
 * @desc StudentController
 */
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MybatisPlusProperties mybatisProperties;

    @GetMapping("/get/{id}")
    public String get(@PathVariable("id") Long id, Model model) throws IllegalAccessException {
        Student student = studentMapper.selectById(id);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Field field : Student.class.getDeclaredFields()) {
            field.setAccessible(true);
            list.add(new HashMap<>() {
                {
                    put("name", field.getName());
                    put("value", field.get(student));
                }
            });
        }

        model.addAttribute("student", list);
        return "student-detail";
    }

    @GetMapping("/list/{pageNum}/{pageSize}")
    public String list(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> list = studentMapper.selectList(new LambdaQueryWrapper<>());
        PageInfo<Student> pageInfo = PageInfo.of(list);
        model.addAttribute("pageInfo", pageInfo);
        return "student-list";
    }
}
