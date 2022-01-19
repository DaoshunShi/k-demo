package com.example.kdemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
    public static void main(String[] args) {

        String str = "logger.e(null, \"实体字段[{}:{}] 索引[{}] 冲突，值[{}]\", entityMeta.name, fieldName, index, value)\n" +
                "   logger.e(null, \"实体字段[{}:{}] 索引[{}] 冲突，值,[{}]\", entityMeta.name, fieldName, index, value)\n" +
                "    @PostMapping(\"task/pause\")\n" +
                "    @ResponseStatus(HttpStatus.NO_CONTENT)\n" +
                "    fun pauseTasks(@RequestParam(\"ids\") idsStr: String) {\n" +
                "        val ids = idsStr.split(\",\")\n" +
                "        var errorMessage = \"\"\n" +
                "        for (id in ids) {\n" +
                "            try {\n" +
                "                taskService.pauseTask(id, \"用户或外1234部系统\")\n" +
                "            } catch (e: Exception) {\n" +
                "                logger.e(e, \"暂停任务失败 id=[{}]\", id)\n" +
                "                errorMessage += \"暂停任务[$id]失败。${e.message}\\n\"\n" +
                "            }\n" +
                "        }\n" +
                "        if (errorMessage.isNotBlank()) throw ObservableError(errorMessage)\n" +
                "    }";
        String pattern = "\"(?=.*[\\u4e00-\\u9fa5])[\\u4e00-\\u9fa5_a-zA-Z0-9\\[\\]\\{\\}\\=\\$.\\,\\.\\\\\\s\\:，]+\"";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());

    }

}
