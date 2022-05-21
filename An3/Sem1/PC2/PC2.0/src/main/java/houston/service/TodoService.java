package houston.service;


import houston.dto.TaskDTO;
import houston.entity.Todo;
import houston.repository.TodosRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodosRepository tasksRepository;

    public TodoService() { }

    public List<Todo> getTasks() {
        return tasksRepository.findAll();
    }

    public Todo getTask(String taskId) {
        Optional<Todo> task = tasksRepository.findById(taskId);
        return task.get();
    }

    public Todo saveTask(TaskDTO taskDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Todo task = modelMapper.map(taskDTO, Todo.class);
        task.setCreatedDate(new Date().toString());
        return tasksRepository.insert(task);
    }

    public Todo updateTask(TaskDTO taskDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Todo task = modelMapper.map(taskDTO, Todo.class);
        task.setUpdatedDate(new Date().toString());
        return tasksRepository.save(task);
    }

    public String deleteTask(String taskId) {
        tasksRepository.deleteById(taskId);
        return taskId;
    }
}