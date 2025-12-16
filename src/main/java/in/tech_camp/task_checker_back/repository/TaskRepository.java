package in.tech_camp.task_checker_back.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.task_checker_back.entity.TaskEntity;

@Mapper
public interface TaskRepository {
  // タスク一覧を取得
  @Select("SELECT * FROM tasks")
  List<TaskEntity> findAll();

  // タスク登録処理
  @Insert(
    "INSERT INTO tasks "
    + "("
    + "name,"
    + "explanation,"
    + "deadline_date,"
    + "status,"
    + "genre_id"
    + ")"
    + " VALUES"
    + "("
    + "#{name},"
    + "#{explanation},"
    + "#{deadlineDate},"
    + "#{status},"
    + "#{genreId}"
    + ")"
  )
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(TaskEntity taskEntity);

  @Select("SELECT EXISTS (SELECT 1 FROM tasks WHERE id = #{id})")
  boolean isExistTask(Integer id);

  @Update("UPDATE tasks "
    + "SET "
    + " id = #{id},"
    + " name = #{name},"
    + " explanation = #{explanation},"
    + " deadline_date = #{deadlineDate},"
    + " status = #{status},"
    + " genre_id = #{genreId}, "
    + " updated_at = #{updatedAt} "
    + "WHERE id = #{id}"
  )
  void update(TaskEntity taskEntity);

  @Delete("DELETE FROM tasks WHERE id = #{id}")
  void delete(Integer id);
}
