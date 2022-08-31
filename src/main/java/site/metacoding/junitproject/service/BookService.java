package site.metacoding.junitproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.web.dto.BookRespDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@RequiredArgsConstructor
@Service
public class BookService {
	
	private final BookRepository bookRepository;
	
	// 1. 책 등록
	@Transactional(rollbackFor = RuntimeException.class)
	public BookRespDto 책등록하기(BookSaveReqDto dto) {
		Book bookPS = bookRepository.save(dto.toEntity());
		return new BookRespDto().toDto(bookPS);
	}
	
	// 2. 책 목록보기
	
	// 3. 책 한건보기
	
	// 4. 책 삭제
	
	// 5. 책 수정
}
