package Model;
/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
import java.util.Date;

public class Transmision {
	public final static String TRANSMISSION_TYPE_FILM = "Pel�cula";
	public final static String TRANSMISSION_TYPE_ARTIST = "Artista";
	public final static String TRANSMISSION_TYPE_ALBUM = "Disco";
	
	private Long id;

	private String tipoItem;
	private Date fecha;
	private HelperItem helperItem;
	private boolean rated;

	public Transmision() {
		// this form used by Hibernate
	}

	public Transmision(String title, Date date) {
		// for application use, to create new events
		this.tipoItem = title;
		this.fecha = date;
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

	public HelperItem getHelperItem() {
		return helperItem;
	}

	public void setHelperItem(HelperItem helperItem) {
		this.helperItem = helperItem;
	}

	public boolean getRated() {
		return rated;
	}

	public void setRated(boolean rated) {
		this.rated = rated;
	}
	
	public String toString(){
		String result = "id: " + getId() + "\n";
		result += "tipo: " + getTipoItem() + "\n";
		result += "fecha: " + getFecha() + "\n";
		result += "rated: " + getRated() + "\n";
		return result;
	}
}