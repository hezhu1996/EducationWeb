package com.hezhu.mpdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.mpdemo.entity.User;
import com.hezhu.mpdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MpdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    //查询user所有数据
    @Test
    void findAll() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    //添加操作
    @Test
    void insert() {
        User user = new User();
        user.setName("Bob09");
        user.setAge(40);
        user.setEmail("bob@gmail.com");
        int insert = userMapper.insert(user);
        System.out.println("insert: "+insert);
    }

    //修改操作
    @Test
    void update() {
        User user = new User();
        user.setId(2L);
        user.setAge(120);

        int row = userMapper.updateById(user);
        System.out.println(row);
    }

    //修改乐观锁
    @Test
    void testOptimisticLocker() {
        //先根据id查询数据
        User user = userMapper.selectById(8L);
        //再进行修改
        user.setAge(200);
        //根据id修改
        userMapper.updateById(user);
    }

    //多id批量查询
    @Test
    void testSelectDemo1() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        System.out.println(users);
    }

    //简单条件查询
    @Test
    void testSelectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Jone");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);

        System.out.println(users);
    }

    //分页
    @Test
    void testPage() {
        //1.创建page对象
        //参数：当前页 和 每页显示记录数
        Page<User> page = new Page<>(1, 3);

        //2.调用mp分页查询方法
        //参数： page 和 条件（wrapper）
        //分页所有数据都封装到page对象中
        userMapper.selectPage(page, null);

        //3.通过page获取分页数据
        System.out.println(page.getCurrent()); //当前页
        System.out.println(page.getRecords()); //每页数据list集合
        System.out.println(page.getSize()); //每页显示记录数
        System.out.println(page.getTotal()); //总记录数
        System.out.println(page.getPages()); //总页数

        System.out.println(page.hasNext()); //是否有下一页
        System.out.println(page.hasPrevious()); //是否有上一页
    }

    //删除 - 物理删除
    @Test
    void testDeleteById() {
        int result = userMapper.deleteById(9L);
        System.out.println(result);
    }

    //批量删除
    @Test
    void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(2L, 3L));
        System.out.println(result);
    }

    //条件删除
    @Test
    public void testDeleteByMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);

        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    //MP复杂查询操作
    @Test
    void testSelectQuery() {
        //创建对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //通过QueryWrapper设置条件
        //ge(>=), gt(>), le(<=), lt(<)
        //查询age>=30
        wrapper.ge("age", 30); //(字段， 数值)
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);

        //eq(=), ne(!=)
        wrapper.eq("name", "Bob");

        //between (a<=x<=b)
        wrapper.between("age", 20, 30);

        //like模糊查询
        wrapper.like("name", "B");

        //orderByDesc(降序排列)
        wrapper.orderByDesc("id"); //根据id降序排列

        //last: 正常sql语句后面拼接一个last中的数值
        wrapper.last("limit 1");

        //查询指定列
        wrapper.select("id", "name"); //只查询id和name字段
    } 

}
