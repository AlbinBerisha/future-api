package io.github.albinberisha.future.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.albinberisha.future.api.repository.UserRepository;

/**
 * @author Albin Berisha
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username, "User.withAll")
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	}
}
