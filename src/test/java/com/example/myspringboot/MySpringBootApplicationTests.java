package com.example.myspringboot;

import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.service.AyUserService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Ignore
@SpringBootTest
class MySpringBootApplicationTests {
    Logger logger = LogManager.getLogger(MySpringBootApplicationTests.class);

    private static final String ALL_USER = "ALL_USER_LIST";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private AyUserService ayUserService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * Spring Boot 集成 MySQL 测试
     */
    @Test
    public void mySqlTest() {
        String sql = "select id, name, password from ay_user";
        List<AyUser> userList = jdbcTemplate.query(sql, (rs, i) -> {
            AyUser user = new AyUser();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        });

        System.out.println("查询成功：");
        for (AyUser user : userList) {
            System.out.println("[id]: " + user.getId() + ", [name]: " + user.getName());
        }
    }

    /**
     * Spring Boot 集成 Spring Data JPA 测试
     */
    @Transactional
    @Test
    public void testRepository() {
        // 查询所有数据
        List<AyUser> userList = ayUserService.findAll();
        System.out.println("findAll: " + userList.size());

        // 通过 name 查询
        List<AyUser> userList2 = ayUserService.findByName("a_yi");
        System.out.println("findByName: " + userList2.size());
        Assert.isTrue(userList2.get(0).getName().equals("a_yi"), "data error!");

        // 通过 name 模糊查询
        List<AyUser> userList3 = ayUserService.findByNameLike("%yi%");
        System.out.println("findByNameLike: " + userList3.size());
        Assert.isTrue(userList3.get(0).getName().equals("a_yi"), "data error!");

        // 通过 id 列表查询
        List<String> ids = new ArrayList<>();
        ids.add("01");
        ids.add("02");
        List<AyUser> userList4 = ayUserService.findByIdIn(ids);
        System.out.println("findByIdIn: " + userList4.size());

        // 分页查询
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AyUser> userList5 = ayUserService.findAll(pageRequest);
        System.out.println("page findAll: " + userList5.getTotalPages() + "/" + userList5.getSize());

        // 新增
        AyUser ayUser = new AyUser();
        ayUser.setId("04");
        ayUser.setName("test4");
        ayUser.setPassword("1234");
        ayUserService.save(ayUser);

        // 删除
        ayUserService.delete("01");
    }

    /**
     * 事务测试
     */
    @Test
    public void testTransaction() {
        AyUser ayUser = new AyUser();
        ayUser.setId("02");
        ayUser.setName("ahua");
        ayUser.setPassword("321");
        ayUserService.save(ayUser);
    }

    /**
     * Redis 测试
     */
    @Test
    public void testRedis() {
        // 新增 key 为 name，value 为 ay
        redisTemplate.opsForValue().set("name", "ay");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println("name: " + name);

        // 更新
        redisTemplate.opsForValue().set("name", "al");
        name = (String) redisTemplate.opsForValue().get("name");
        System.out.println("new name: " + name);

        // 删除
        redisTemplate.delete("name");

        // 查询
        name = (String) redisTemplate.opsForValue().get("name");
        System.out.println("query name: " + name);
    }

    @Test
    public void testFindById() {
        AyUser ayUser = ayUserService.findById("01");
        System.out.println("----->>> id: " + ayUser.getId() + " name: " + ayUser.getName());
        Long redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("缓存中的用户数量：" + redisUserSize);

        AyUser ayUser1 = ayUserService.findById("02");
        System.out.println("----->>> id: " + ayUser1.getId() + " name: " + ayUser1.getName());
        redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("缓存中的用户数量：" + redisUserSize);

        AyUser ayUser2 = ayUserService.findById("04");
        System.out.println("----->>> id: " + ayUser2.getId() + " name: " + ayUser2.getName());
        redisUserSize = redisTemplate.opsForList().size(ALL_USER);
        System.out.println("缓存中的用户数量：" + redisUserSize);
    }

    @Test
    public void testLog4j() {
        ayUserService.delete("02");

        logger.info("delete success!!!");
    }

    @Test
    public void testMybatis() {
        AyUser ayUser = ayUserService.findByNameAndPassword("a_yi", "123456");
        logger.info(ayUser.getId() + " " + ayUser.getName());
    }

    @Test
    void contextLoads() {
    }

}
