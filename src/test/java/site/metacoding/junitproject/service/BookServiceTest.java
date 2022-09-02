package site.metacoding.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
