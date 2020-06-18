package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleService {
    @Resource
    RoleRepository roleRepository;
}
