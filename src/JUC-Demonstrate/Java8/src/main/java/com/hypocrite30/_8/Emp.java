package com.hypocrite30._8;

import java.math.BigDecimal;

/**
 * @Description: 作为 TestGenericDao案例的实体类
 * @Author: Hypocrite30
 * @Date: 2021/8/4 15:37
 */
class Emp {
    private int empno;
    private String ename;
    private String job;
    private BigDecimal sal;

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", sal=" + sal +
                '}';
    }
}

