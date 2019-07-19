package com.sprint.todo.Controllers;

import com.sprint.todo.models.Role;
import com.sprint.todo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController
{
    @Autowired
    RoleService roleService;



    @GetMapping(value = "/roles", produces = {"application/json"})
    public ResponseEntity<?> listRoles(HttpServletRequest request)
    {

        List<Role> allRoles = roleService.findAll();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }


    @GetMapping(value = "/role/{roleId}", produces = {"application/json"})
    public ResponseEntity<?> getRole(HttpServletRequest request, @PathVariable Long roleId)
    {
        Role r = roleService.findRoleById(roleId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @PostMapping(value = "/role")
    public ResponseEntity<?> addNewRole(HttpServletRequest request, @Valid @RequestBody Role newRole) throws URISyntaxException
    {
        newRole = roleService.save(newRole);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{roleid}")
                .buildAndExpand(newRole.getRoleid())
                .toUri();
        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @DeleteMapping("/role/{id}")
    public ResponseEntity<?> deleteRoleById(HttpServletRequest request, @PathVariable long id)
    {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}