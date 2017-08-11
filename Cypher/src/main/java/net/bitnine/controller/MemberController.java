package net.bitnine.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import net.bitnine.domain.Member;
import net.bitnine.persistence.MemberRepository;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/member/")
public class MemberController {
  
  @Autowired
  PasswordEncoder pwEncoder;
  
  @Autowired
  MemberRepository repo;

  @GetMapping("/join")
  public void join(){
    
  }

/*  @PostMapping("/join")
  public String joinPost(@ModelAttribute("member")Member member){
  
    log.info("MEMBER: " + member);
    
    return "/member/joinResult";
  }*/
  
  @Transactional
  @PostMapping("/join")
  public String joinPost(@ModelAttribute("member") Member member) {

    log.info("MEMBER: " + member);

    String encryptPw = pwEncoder.encode(member.getMpw());

    log.info("en: " + encryptPw);

    member.setMpw(encryptPw);

    repo.save(member);

    return "/member/joinResult";
  }


}
