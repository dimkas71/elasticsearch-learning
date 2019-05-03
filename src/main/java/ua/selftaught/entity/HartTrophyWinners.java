package ua.selftaught.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hart_trophy_winners")
public class HartTrophyWinners {
	@Id
	private Long id;
	
	private String season;
	private String winner;
	private String team;
	private String position;
	private int win;
}
