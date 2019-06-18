package com.e3.service;

import com.e3.common.pojo.E3Result;
import com.e3.pojo.TbUser;

public interface UserService {

    E3Result checkData(String param,Integer type);

    E3Result register(TbUser user);

    E3Result login(String username,String password);

    E3Result getUserInfo(String token);
}
