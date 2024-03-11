package com.apiauth.concrete;

import java.util.UUID;

import com.apiauth.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRole extends BaseEntity<UUID> {

    @PrimaryKeyJoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @PrimaryKeyJoinColumn(name = "role_id")
    @ManyToOne
    private Role role;
}
