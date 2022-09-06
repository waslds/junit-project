package site.metacoding.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.BookRespDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	@InjectMocks
	private BookService bookService;
	
	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private MailSender mailSender;
	
	@Test
	public void 책등록하기_테스트() {
		// given
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("junit강의");
		dto.setAuthor("메타코딩");
		
		// stub (가설)
		when(bookRepository.save(any())).thenReturn(dto.toEntity());
		when(mailSender.send()).thenReturn(true);
		
		// when
		BookRespDto bookRespDto = bookService.책등록하기(dto);
		
		// then
		assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
	}
	
	@Test
	public void 책목록보기_테스트() {
		// given(파라미터로 들어올 데이터)
		
		// stub(가설)
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "junit강의", "메타코딩"));
		books.add(new Book(2L, "spring강의", "겟인데어"));
		when(bookRepository.findAll()).thenReturn(books);
		
		// when(실행)
		List<BookRespDto> bookRespDtoList = bookService.책목록보기();
		
		// then(검증)
		assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo("junit강의");
		assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo("메타코딩");
		assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo("spring강의");
		assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo("겟인데어");
	}
	
	@Test
	public void 책한건보기_테스트() {
		// given
		Long id = 1L;
		
		// stub
		Book book = new Book(id, "junit강의", "메타코딩");
		Optional<Book> bookOP = Optional.of(book);
		when(bookRepository.findById(id)).thenReturn(bookOP);
		
		// when
		BookRespDto bookRespDto = bookService.책한건보기(id);
		
		// then
		assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
	}
	
	@Test
	public void 책수정하기_테스트() {
		// given
		Long id = 1L;
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("spring강의");
		dto.setAuthor("겟인데어");
		
		// stub
		Book book = new Book(id, "junit강의", "메타코딩");
		Optional<Book> bookOP = Optional.of(book);
		when(bookRepository.findById(id)).thenReturn(bookOP);
		
		// when
		BookRespDto bookRespDto = bookService.책수정하기(id, dto);
		
		// then
		assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
	}
}
