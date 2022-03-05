package com.mrpeng.testexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;


public class TestExcel {
    public static void main(String[] args) {
        read();

    }
    public static void write(){
        String path ="D:/logs/student.xlsx";
        //ExcelData表示表头
        List<ExcelData> list =new ArrayList<>();
        for (int i =0; i<20;i++){
            list.add(new ExcelData("pqq"+i,i+5));
        }
        EasyExcel.write(path,ExcelData.class).sheet().doWrite(list);
    }
    public static void read(){
        String path ="D:/logs/student.xlsx";
        EasyExcel.read(path,ExcelData.class,new ExcelListener()).sheet().doRead();
    }
}
