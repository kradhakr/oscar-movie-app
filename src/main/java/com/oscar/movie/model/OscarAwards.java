package com.oscar.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oscar_award")
public class OscarAwards extends AuditModel{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "oscar_year")
	private String oscarYear;

	@Column(name = "category")
	private String category;

	@Column(name = "nominee", length = 1000)
	private String nominee;

	@Column(name = "additional_info")
	private String additionalInfo;

	@Column(name = "won_award")
	private Boolean wonAward;

	public OscarAwards(String oscarYear, String category, String nominee, String additionalInfo, Boolean wonAward) {
		this.oscarYear = oscarYear;
		this.category = category;
		this.nominee = nominee;
		this.additionalInfo = additionalInfo;
		this.wonAward = wonAward;
	}
}
