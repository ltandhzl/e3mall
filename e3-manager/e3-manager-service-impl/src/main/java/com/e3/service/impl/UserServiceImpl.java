package com.e3.service.impl;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.JedisClient;
import com.e3.common.pojo.JsonUtils;
import com.e3.mapper.TbUserMapper;
import com.e3.pojo.TbUser;
import com.e3.pojo.TbUserExample;
import com.e3.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Override
    public E3Result checkData(String param, Integer type) {
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (type==1){
            criteria.andUsernameEqualTo(param);
        }else if(type==2){
            criteria.andPhoneEqualTo(param);
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (tbUsers!=null && tbUsers.size()>0){
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser user) {
        if (StringUtils.isBlank(user.getUsername())){
            return E3Result.build(202,"用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())){
            return E3Result.build(202,"密码不能为空");
        }
        if (StringUtils.isBlank(user.getPhone())){
            return E3Result.build(202,"手机不能为空");
        }
        if(!(boolean)checkData(user.getUsername(),1).getData()){
            return E3Result.build(400,"用户名或者密码错误！");
        }
        if (!(boolean) checkData(user.getPhone(),2).getData()){
            return E3Result.build(400,"用户名或者密码错误！");
        }
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        user.setUpdated(new Date());
        user.setCreated(new Date());
        tbUserMapper.insert(user);
        return E3Result.ok();
    }
    @Override
    public E3Result login(String username, String password) {
        if (StringUtils.isBlank(username)){
            return E3Result.build(200,"用户名不能为空!");
        }
        if (StringUtils.isBlank(password)){
            return E3Result.build(200,"用户名不能为空!");
        }
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (tbUsers==null || tbUsers.size()==0){
            return E3Result.build(400,"用户名不存在，请重新登录!");
        }
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!tbUsers.get(0).getPassword().equals(md5Pass)){
            return E3Result.build(400,"用户名或者密码错误!");
        }
        String token = UUID.randomUUID().toString();
        jedisClient.set("SESSION_TOKEN:"+token, JsonUtils.objectToJson(tbUsers.get(0)));
        jedisClient.expire("SESSION_TOKEN:"+token,SESSION_EXPIRE);
        return E3Result.ok(token);
    }

    @Override
    public E3Result getUserInfo(String token) {
        String user = jedisClient.get("SESSION_TOKEN:" + token);
        if (StringUtils.isBlank(user)){
            return E3Result.build(400,"用户信息已过期，请重新登陆！");
        }
        jedisClient.expire("SESSION_TOKEN:" + token,SESSION_EXPIRE);
        TbUser tbUser = JsonUtils.jsonToPojo(user, TbUser.class);
        return E3Result.ok(tbUser);
    }
}
