package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	//Diferenca de Falha e Erro
	// Erro é quando eu não consigo completar a execucao de um teste
	//Falha é quando eu até executo o teste, mas a assertiva não é satisfeita.
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this); //faz a instancia do controle e injeta os mocks de TaskRepo 
											// dentro de controller
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task todo=new Task();
		todo.setDueDate(LocalDate.now());
		
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo=new Task();
		todo.setTask("Minha Descricao");
		
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo=new Task();
		todo.setTask("Minha Descricao");
		todo.setDueDate(LocalDate.of(2010, 01, 01));

		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo=new Task();
		todo.setTask("Minha Descricao");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		
		
		//mockito verifica se o taskRepo foi invocada no metodo salvar enviando o parametro "todo"
		Mockito.verify(taskRepo).save(todo);
	}
}
