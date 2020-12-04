package com.example.test.streamTest;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class StreamTest {

    @Test
    void testStream(){
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        list.stream().filter(x -> x>6).forEach(System.out::println);
        List<Integer> listLessFive = list.stream().filter(x -> x < 5).collect(Collectors.toList());
    }

    @Test
    void testSort(){
        List<String> strList = Arrays.asList("asd","13c","阿斯顿撒旦","手动12");
        List<Integer> integerList = Arrays.asList(1,32,21,5,3,-2);
        strList.sort((s1,s2) -> s1.compareTo(s2));
        strList.stream().forEach(System.out::println);
        integerList.sort( (i1,i2) -> i1.compareTo(i2) );
        integerList.stream().forEach(System.out::println);

        List<Man> manList = new ArrayList<>();
        manList.add(new Man("后备箱", 12));
        manList.add(new Man("挡风玻璃", 24));
        manList.add(new Man("防风玻璃", 24));
        manList.add(new Man("方向盘", 10));
        manList.add(new Man("皮座椅",7));
        //1
        manList.sort(new Comparator<Man>() {
            @Override
            public int compare(Man o1, Man o2) {
                return o1.getAge().compareTo(o2.getAge());
            }
        });
        manList.stream().forEach(System.out::println);
        //2
        manList.sort(Comparator.comparing(Man::getAge));//正序（默认）
        manList.sort(Comparator.comparing(Man::getAge).reversed());//倒序
        manList.sort(Comparator.comparing(Man::getAge).thenComparing(Man::getName));//先age正序，再name正序
        manList.sort(Comparator.comparing(Man::getAge).thenComparing(Man::getName).reversed());//先age正序，再name倒序
        manList.sort(Comparator.comparing(Man::getAge).reversed().thenComparing(Man::getName).reversed());
        manList.stream().forEach(System.out::println);
        //3
        manList.sort((m1,m2)->{
            if (m1.getAge()>m2.getAge()){
                return -1;
            }
            return 1;
        });
        manList.stream().forEach(System.out::println);
        //4
        List<String> collect = manList.stream().map(n -> n.getName())
                .collect(Collectors.toList());
//        collect.sort((n1,n2) -> n1.compareTo(n2));
        collect.stream().forEach(System.out::println);

    }

    @Data
    class Man {
        String name ;
        Integer age;
        Man(String name, Integer age){
            this.name = name;
            this.age = age;
        }
    }



}
