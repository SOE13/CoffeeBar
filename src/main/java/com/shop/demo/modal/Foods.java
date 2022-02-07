package com.shop.demo.modal;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Entity
public class Foods {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;
		@NotBlank(message = "Please fill the Name")
		private String name;
		@NotNull(message = "Please fill the Price")
		private Integer price;
		@Min(value = 1,message = "Please select One Category")
		private Integer cat;
		private String img;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getPrice() {
			return price;
		}
		public void setPrice(Integer price) {
			this.price = price;
		}
		public Integer getCat() {
			return cat;
		}
		public void setCat(Integer cat) {
			this.cat = cat;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
}
