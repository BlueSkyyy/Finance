package cn.com.xbed.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author：Tom
 * @create 2017-03-03 14:03
 **/
@RestController
@RequestMapping("test")
public class UserController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(){
        return "Hello SpringBoot";
    }

}
