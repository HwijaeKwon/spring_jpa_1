package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue // em.persist 이후에 id를 생성해서 영속성 컨텍스트에 저장해둠
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

/*    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // order table에 있는 member field (member_id)에 의해 mapping 된 것이다
    // order 객체의 member가 가리키는 order table의 member_id 필드에 의해 mapping 된 것이다
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();*/
}
