package in.tech_camp.task_checker_back.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tech_camp.task_checker_back.entity.TaskEntity;
import in.tech_camp.task_checker_back.repository.TaskRepository;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

  private final TaskRepository taskRepository;
  
  /**
   * タスク一覧取得
   * @return タスク一覧
   */
  @GetMapping("/")
  public List<TaskEntity> getTasks() {
    return taskRepository.findAll();
  }
  
  /**
   * タスク登録処理
   * @param taskEntity
   * @return タスク一覧
   */
  @PostMapping("/")
  public ResponseEntity<?> createTask(@RequestBody TaskEntity taskEntity) {
    try {
      taskRepository.insert(taskEntity);
      List<TaskEntity> response = taskRepository.findAll();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("messages", List.of("unsuccess to create task")));
    }
  }

  @PutMapping("/{taskId}/update")
  public ResponseEntity<?> updateTask(@PathVariable("taskId") Integer id, @RequestBody TaskEntity taskEntity) {
    boolean isExistTask = taskRepository.isExistTask(id);
    if (!isExistTask) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("messages", List.of("Task not found")));
    }
    try {
      taskEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
      taskRepository.update(taskEntity);
      List<TaskEntity> response = taskRepository.findAll();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("messages", List.of("unsuccess to update task")));
    }
  }

  @DeleteMapping("/{taskId}/delete")
  public  ResponseEntity<?> deleteTask(@PathVariable("taskId") Integer id) {
    boolean isExistTask = taskRepository.isExistTask(id);
    if (id == null || !isExistTask) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("messages", List.of("削除対象のタスクがありません")));
    try {
      taskRepository.delete(id);
      List<TaskEntity> response = taskRepository.findAll();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("messages", List.of("unsuccess to delete task")));
    }
  }
}
