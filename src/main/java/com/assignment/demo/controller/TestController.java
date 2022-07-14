package com.assignment.demo.controller;

import com.assignment.demo.model.CategoryCodeClass;
import com.assignment.demo.model.OldNew;
import com.assignment.demo.service.CategoryCodeClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/FetchNextNumber")
@CrossOrigin("*")
public class TestController {

    @Autowired
    OldNew oldNew;


    @Autowired
    CategoryCodeClassServiceImpl categoryCodeClassService;


    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody CategoryCodeClass input) throws Exception {


        int code =input.getCategoryCode();

        int value = categoryCodeClassService.codeByValue(code);

        OldNew oldNew1=oldNew;
        oldNew1.setOldValue(value);

        int ans=value+10-sumOfIndividualDigit(value);


        Thread thread = new Thread("New Thread") {


            public void run(){
                try {
                    Thread.sleep(5000);
                    System.out.println("number :" + value +" ans is"+ans );

                    categoryCodeClassService.setValueByCode(code,ans);
                    oldNew1.setNewValue(ans);


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("run by: " + getName());
            }
        };


        thread.start();




        return ResponseEntity.ok(oldNew1);



    }


    static int sumOfIndividualDigit(int n) {
        int sum = 0;

        while (n > 0 || sum > 9) {
            if (n == 0) {
                n = sum;
                sum = 0;
            }
            sum = sum + n % 10;
            n = n / 10;
        }
        return sum;
    }
}


