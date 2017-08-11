package net.bitnine.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "mid")
@ToString
public class Member {

  @Id
  private String mid;
  
  private String mpw;

  //@OneToMany//(mappedBy = "fno", orphanRemoval = true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
  
  /*@OneToOne
  private MemberRole role;*/
  
  @ManyToOne 
  @JoinColumn(name="rid")
  private MemberRole role;
  
 /* private String uname;
  
  @CreationTimestamp
  private Timestamp regdate;
  
  @UpdateTimestamp
  private Timestamp updatedate;
  
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
  @JoinColumn(name = "member")
  private List<MemberRole> roles;*/

}
