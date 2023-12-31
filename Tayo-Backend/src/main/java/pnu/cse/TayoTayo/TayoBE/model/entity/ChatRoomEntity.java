package pnu.cse.TayoTayo.TayoBE.model.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity { // 채팅방 느낌임

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoomEntity")
    private List<ChatMessageEntity> chatMessageEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id")
    private MemberEntity fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    private MemberEntity toMember;

    private String lastMessage;

    private Timestamp createdAt; // 생성 시각
    private Timestamp updatedAt;

    @PrePersist
    void registeredAt(){
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt(){ this.updatedAt = Timestamp.from(Instant.now()); }


    @Builder
    public ChatRoomEntity(MemberEntity fromMember, MemberEntity toMember) {
        //this.id = id;
        this.fromMember = fromMember;
        this.toMember = toMember;
        //this.lastMessage = lastMessage;
    }

    public void addChatMessage(ChatMessageEntity chatMessageEntity){
        chatMessageEntity.setChatRoomEntity(this);
        this.chatMessageEntities.add(chatMessageEntity);
        this.lastMessage = chatMessageEntity.getContent();
    }

}
/*
    1. 채팅방 목록 조회하면 fromId랑 toId 중에 해당 userId가 있는거 다 긁어 와야함
    2. 채팅방 생성 시에는 fromId가
    3. 구독은 ChatHeaderEntity Id를 기준으로..?

    그리고
    fromMember랑 toMember를 인덱스처리해주는게 좋을듯?
 */
