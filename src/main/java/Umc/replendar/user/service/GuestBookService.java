package Umc.replendar.user.service;

import Umc.replendar.user.converter.GuestBookConverter;
import Umc.replendar.user.dto.req.GuestBookDtoReq;
import Umc.replendar.user.dto.res.GuestBookDtoRes;
import Umc.replendar.user.entity.GuestBook;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.GuestBookRepository;
import Umc.replendar.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;
    private final UserRepository userRepository;

    //방명록 작성
    public void createGuestBook(Long userId, GuestBookDtoReq requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. userId = " + userId));

        GuestBook guestBook = GuestBookConverter.toEntity(user, requestDto);
        guestBookRepository.save(guestBook);
    }

    //방명록 조회
    @Transactional(readOnly = true)
    public List<GuestBookDtoRes> getAllGuestBooks() {
        List<GuestBook> guestBooks = guestBookRepository.findAll();
        return guestBooks.stream()
                .map(GuestBookConverter::toResDto)
                .collect(Collectors.toList());
    }
}
