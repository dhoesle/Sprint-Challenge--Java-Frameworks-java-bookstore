package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.models.Wrote;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;



//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;
    
    @Autowired
    SectionService sectionService;
    
    @Autowired
    AuthorService authorService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void a_findAll()
    {
        assertEquals(5, bookService.findAll().size());
    }
    
    @Test
    public void b_findBookById()
    {
        assertEquals("Flatterland", bookService.findBookById(26).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void c_notFindBookById()
    {
        assertEquals("Flatterland", bookService.findBookById(1).getTitle());
    }

    @Test
    public void e_delete()
    {
        bookService.delete(26);
        assertEquals(4, bookService.findAll().size());
    }

    @Test
    public void f_save()
    {
        Section s1 = new Section("Fiction");
        s1 = sectionService.save(s1);
    
        Author a6 = new Author("Ian", "Stewart");
        a6 = authorService.save(a6);
    
    
        Book b1 = new Book("TEST BOOK", "9780738206752", 2001, s1);
        b1.getWrotes()
                .add(new Wrote(a6, new Book()));
        Book saveB1 = bookService.save(b1);
    
        System.out.println("*** DATA ***");
        System.out.println(saveB1);
        System.out.println("*** DATA ***");
        
        assertEquals("TEST BOOK", saveB1.getTitle());
    }

    @Test
    public void update()
    {
    }

    @Test
    public void deleteAll()
    {
    }
}