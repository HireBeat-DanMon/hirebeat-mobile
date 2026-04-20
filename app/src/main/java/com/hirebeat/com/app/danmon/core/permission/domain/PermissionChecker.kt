package com.hirebeat.com.app.danmon.core.permission.domain

interface PermissionChecker {
    fun hasLocationPermission(): Boolean
}