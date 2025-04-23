package com.beiming.demo.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.beiming.demo.domain.DialogIntention;
import com.beiming.demo.mapper.DialogIntentionMapper;
import com.beiming.demo.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DialogIntentionController
 */
@RestController
@RequestMapping("intention")
public class DialogIntentionController {
    @Resource
    private DialogIntentionMapper dialogIntentionMapper;

    @GetMapping("convert")
    public void convert() {
        List<DialogIntention> dialogIntentions = dialogIntentionMapper.selectList(null);
        for (DialogIntention dialogIntention : dialogIntentions) {
            String keyword = dialogIntention.getKeyword();
            List<String> list = List.of(keyword.split(";"));
//            List<String> list = JsonUtils.toObject(keyword,new TypeReference<List<String>>() {});
            List<String> list1 = list.stream().filter(StringUtils::isNoneBlank).toList();
            dialogIntentionMapper.update(
                    new LambdaUpdateWrapper<DialogIntention>()
                            .eq(DialogIntention::getId, dialogIntention.getId())
                            .set(DialogIntention::getKeyword, JsonUtils.toJson(list1))
            );
        }
    }

}
