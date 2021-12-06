package com.kkb.service;

import com.kkb.mapper.AdminsMapper;
import com.kkb.pojo.Admins;
import com.kkb.pojo.AdminsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminsMapper adminsMapper;

    public Admins login(Admins admins){
        AdminsExample example = new AdminsExample();
        AdminsExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(admins.getLoginName());
        criteria.andAdminPwdEqualTo(admins.getAdminPwd());
        List<Admins> list = adminsMapper.selectByExample(example);
        if (list.size()>0){
            list.get(0).setAdminPwd(null);
            return list.get(0);
        }
        return null;
    }
}
