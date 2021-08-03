package com.demo.test.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "post")
public class PostsDTO extends PanacheEntityBase{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name = "title")
	public String title;
	
	@Column(name = "content")
	public String content;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name = "posts_tags_relation",
			joinColumns = @JoinColumn(name = "posts_id"),
			inverseJoinColumns = @JoinColumn(name = "tags_id"))
	@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@tagsId")
	public Set<TagsDTO> tags = new HashSet<>();

}
