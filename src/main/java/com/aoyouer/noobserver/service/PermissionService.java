package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PermissionService {
    @Resource
    PermissionRepository permissionRepository;
}
