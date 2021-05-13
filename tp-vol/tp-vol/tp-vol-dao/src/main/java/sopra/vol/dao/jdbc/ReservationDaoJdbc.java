package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IReservationDao;
import sopra.vol.model.Reservation;
import sopra.vol.model.StatutReservation;

public class ReservationDaoJdbc implements IReservationDao {

	@Override
	public List<Reservation> findAll() {
		List<Reservation> resas = new ArrayList<Reservation>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM reservation");

			rs = ps.executeQuery();

			while (rs.next()) {
				Integer id = rs.getInt(1);
				Date dt_reservation = rs.getDate("DT_Reservation");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("Statut_Reservation"));
				Long Passager_id = rs.getLong("Passager_id");
				Long Client_id = rs.getLong("Client_Id");
			
				Reservation resa = new Reservation();
				resa.setDtReservation(dt_reservation);
				resa.setNumero(id);
				resa.setPassager(Application.getInstance().getPassagerDao().findById(Passager_id));
				resa.setClient(Application.getInstance().getClientDao().findById(Client_id));
				resa.setStatut(statut);
				
				resas.add(resa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resas;
	}

	@Override
	public Reservation findById(Integer id) {
		Reservation reservation = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM Reservation WHERE id = ?");

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Date dt_reservation = rs.getDate("DT_Reservation");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("Statut_Reservation"));
				Long Passager_id = rs.getLong("Passager_id");
				Long Client_id = rs.getLong("Client_Id");
			
				Reservation resa = new Reservation();
				resa.setDtReservation(dt_reservation);
				resa.setNumero(id);
				resa.setPassager(Application.getInstance().getPassagerDao().findById(Passager_id));
				resa.setClient(Application.getInstance().getClientDao().findById(Client_id));
				resa.setStatut(statut);
				return resa;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return reservation;
	}


	@Override
	public void create(Reservation obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO reservation (DT_RESERVATION,STATUT_RESERVATION,PASSAGER_ID,CLIENT_ID) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setDate(1, new java.sql.Date(obj.getDtReservation().getTime()));
			ps.setString(2, obj.getStatut().toString());
			if(obj.getPassager()!=null) {
				ps.setLong(3, obj.getPassager().getId());
			}
			else {
				ps.setNull(3, Types.INTEGER);
			}
			if(obj.getClient()!=null) {
				ps.setLong(4, obj.getClient().getId());	
			}
			else {
				ps.setNull(4, Types.INTEGER);
			}
			
			
			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Integer id = keys.getInt(1);
					obj.setNumero(id);
				}
			} else {
				throw new SQLException("Insertion en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Reservation obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE reservation SET DT_RESERVATION=?,STATUT_RESERVATION=?,PASSAGER_ID=?,CLIENT_ID=? WHERE id = ?");

			ps.setDate(1, new java.sql.Date(obj.getDtReservation().getTime()));
			ps.setString(2, obj.getStatut().toString());
			if(obj.getPassager()!=null) {
				ps.setLong(3, obj.getPassager().getId());
			}
			else {
				ps.setNull(3, Types.INTEGER);
			}
			if(obj.getClient()!=null) {
				ps.setLong(4, obj.getClient().getId());	
			}
			else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.setInt(5, obj.getNumero());
			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Mise à jour en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Reservation obj) {
		deleteById(obj.getNumero());
		
	}

	@Override
	public void deleteById(Integer id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM reservation WHERE id = ?");

			ps.setInt(1, id);

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Suppression en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
