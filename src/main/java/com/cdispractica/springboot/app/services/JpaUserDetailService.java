package com.cdispractica.springboot.app.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdispractica.springboot.app.models.dao.IUsuarioDao;
import com.cdispractica.springboot.app.models.entity.Role;
import com.cdispractica.springboot.app.models.entity.Usuario;

@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailService.class);

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("El usuario: "+username+" no existe, Favor de verificar tu username");
			throw new UsernameNotFoundException("No se tiene registro de: "+username);
		}
		
		List<GrantedAuthority> autorities = new ArrayList<GrantedAuthority>();
		
		for(Role role : usuario.getRoles()) {
			logger.info(username+"-Roles: ".concat(role.getAuthority()));
			autorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if(autorities.isEmpty()) {
			logger.info("El usuario: "+username+" no tiene roles, favor de comunicarse con el administrador");
			throw new UsernameNotFoundException("El usuario: "+username+" necesita Roles por asignar");
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, autorities);
			
	}

}
