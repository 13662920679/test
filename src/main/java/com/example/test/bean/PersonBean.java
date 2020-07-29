package com.example.test.bean;


import javax.persistence.*;

@Entity
@Table(name = "PERSON")
public class PersonBean {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Long id;
    private String name;
    private int sex;
    private int age;
    private String idCardNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
}
