package io.github.albinberisha.future.api.domain;

import java.util.Collection;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@NamedEntityGraph(
		name = "User.withAll",
		attributeNodes = {
				@NamedAttributeNode(value = "role", subgraph = "roleWithPermissions"),
				@NamedAttributeNode("merchant")
		},
		subgraphs = {
				@NamedSubgraph(
						name = "roleWithPermissions",
						attributeNodes = @NamedAttributeNode("permissions")
				)
		}
)
@Entity
@Table(name = "user")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7640530677419272463L;
	@Id
	@Column(name = "id", length = 36, nullable = false)
	private String id;
	@Column(name = "email", length = 320, unique = true, nullable = false)
	private String email;
	@Column(name = "username", length = 16, unique = true, nullable = false)
	private String username;
	@Column(name = "password", length = 16, nullable = false)
	private String password;
	@Column(name = "first_name", length = 32, nullable = false)
	private String firstName;
	@Column(name = "last_name", length = 32, nullable = false)
	private String lastName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private UserRole role;
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id", nullable = true)
	private Merchant merchant;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getPermissions().stream().map(p -> (GrantedAuthority) p::name).toList();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@PrePersist
	public void handlePrePersist() {
		if (id == null)
			id = UUID.randomUUID().toString();
	}
}
