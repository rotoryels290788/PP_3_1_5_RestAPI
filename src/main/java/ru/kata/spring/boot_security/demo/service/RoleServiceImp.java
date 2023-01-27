package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

private final RoleDao roleDao;
@Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    @Transactional
    public List<Role> getList() {
        return roleDao.getList();
    }


    @Override
    @Transactional
    public Role getRoleId(Long id) {
        return roleDao.getRole(id);
    }

    @Override
    @Transactional
    public List<Role> listByName(List<String> name) {
        return roleDao.listByName(name);
    }

    @Override
    @Transactional
   public boolean saveRole(Role role) {
        Role userSet = roleDao.findByName(role.getRole());
        if(userSet != null) {return false;}
        roleDao.saveRole(role);
        return true;
    }


}
