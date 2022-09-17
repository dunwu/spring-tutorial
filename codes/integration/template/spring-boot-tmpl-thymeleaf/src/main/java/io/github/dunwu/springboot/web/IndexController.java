/*
 * =============================================================================
 *
 * Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =============================================================================
 */
package io.github.dunwu.springboot.web;

import io.github.dunwu.springboot.dto.StudentDTO;
import io.github.dunwu.springboot.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @RequestMapping("/displayError")
    public String displayError() throws Exception {
        throw new Exception("发生错误");
    }

    @RequestMapping("/displayError2")
    public String displayError2() throws MyException {
        throw new MyException("发生错误2");
    }

    @RequestMapping("/")
    public String getData(Model model) {
        List<StudentDTO> list = new ArrayList<>();
        list.add(new StudentDTO("张三", 20, "北京"));
        list.add(new StudentDTO("李四", 30, "上海"));
        list.add(new StudentDTO("王五", 40, "河北"));
        list.add(new StudentDTO("赵六", 50, "山西"));
        model.addAttribute("list", list);
        return "/index";
    }

}
