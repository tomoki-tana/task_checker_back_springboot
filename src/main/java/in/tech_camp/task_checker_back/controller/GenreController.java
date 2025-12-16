package in.tech_camp.task_checker_back.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tech_camp.task_checker_back.entity.GenreEntity;
import in.tech_camp.task_checker_back.repository.GenreRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class GenreController {

  private final GenreRepository genreRepository;

  /**
   * ジャンル一覧取得
   * @return ジャンル一覧
   */
  @GetMapping("/")
  public List<GenreEntity> findAll() {
    return genreRepository.findAll();
  }

  /**
   * ジャンル登録
   * @param genreEntity
   * @return
   */
  @PostMapping("/")
  public ResponseEntity<?> addGenre(@RequestBody GenreEntity genreEntity) {
    try {
      genreRepository.insert(genreEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("messages", List.of("Internal Server Error")));
    }
    List<GenreEntity> genres = genreRepository.findAll();
    return ResponseEntity.ok().body(genres);
  }

  @PostMapping("/delete")
  public ResponseEntity<?> deleteGenre(@RequestBody GenreEntity genreEntity) {
    try {
      genreRepository.delete(genreEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("messages", List.of("unsuccess to delete genre")));
    }
    List<GenreEntity> genres = genreRepository.findAll();
    return ResponseEntity.ok().body(genres);
  }
  
}
