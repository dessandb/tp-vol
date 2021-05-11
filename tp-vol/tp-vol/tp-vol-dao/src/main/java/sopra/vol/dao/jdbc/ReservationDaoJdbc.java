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
import sopra.vol.dao.IClientDao;
import sopra.vol.dao.IPassagerDao;
import sopra.vol.model.Reservation;
import sopra.vol.model.StatutReservation;
import sopra.vol.model.Client;
import sopra.vol.model.Passager;

public class ReservationDaoJdbc implements IReservationDao {

	@Override
	public List<Reservation> findAll() {
		List<Reservation> reservations = new ArrayList<Reservation>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT numero, dt_reservation, statut_reservation, passager_id, client_id FROM reservation");

			rs = ps.executeQuery();

			while (rs.next()) {
				Integer numero = rs.getInt("numero");
				Date dtReservation = rs.getDate("dt_reservation");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("statut_reservation"));
				Long passagerId = rs.getLong("passager_id");
				Long clientId = rs.getLong("client_id");

				Reservation reservation = new Reservation(numero, dtReservation, statut);
				
				if (passagerId != null) {
					IPassagerDao passagerDao = Application.getInstance().getPassagerDao();

					Passager passager = passagerDao.findById(passagerId);
					reservation.setPassager(passager);
				}
				
				if (clientId != null) {
					IClientDao clientDao = Application.getInstance().getClientDao();

					Client client = clientDao.findById(clientId);
					reservation.setClient(client);
				}

				reservations.add(reservation);
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

		return reservations;
	}

	@Override
	public Reservation findById(Integer numero) {
		Reservation reservation = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT dt_reservation, statut_reservation, passager_id, client_id FROM reservation where numero = ?");

			ps.setInt(1, numero);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				Date dtReservation = rs.getDate("dt_reservation");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("statut_reservation"));
				Long passagerId = rs.getLong("passager_id");
				Long clientId = rs.getLong("client_id");

				reservation = new Reservation(numero, dtReservation, statut);
				
				if (passagerId != null) {
					IPassagerDao passagerDao = Application.getInstance().getPassagerDao();

					Passager passager = passagerDao.findById(passagerId);
					reservation.setPassager(passager);
				}
				
				if (clientId != null) {
					IClientDao clientDao = Application.getInstance().getClientDao();

					Client client = clientDao.findById(clientId);
					reservation.setClient(client);
				}
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
	public void create(Reservation reservation) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO reservation (numero, dt_reservation, statut_reservation, passager_id, client_id) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			//numero
			ps.setInt(1, reservation.getNumero());
			
			//date de reservation (date)
			if (reservation.getDtReservation() != null) {
				ps.setDate(2, new java.sql.Date(reservation.getDtReservation().getTime()));
			} else {
				ps.setNull(2, Types.DATE);
			}
			
			//statut (enum)
			if (reservation.getStatut() != null) {
				ps.setString(3, reservation.getStatut().toString());
			} else {
				ps.setNull(3, Types.VARCHAR);
			}
			
			//passager (lien)
			if (reservation.getPassager() != null && reservation.getPassager().getId() != null) {
				ps.setLong(4, reservation.getPassager().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			
			//client (lien)
			if (reservation.getClient() != null && reservation.getClient().getId() != null) {
				ps.setLong(5, reservation.getClient().getId());
			} else {
				ps.setNull(5, Types.INTEGER);
			}

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Integer numero = keys.getInt(1);
					reservation.setNumero(numero);
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
	public void update(Reservation reservation) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE reservation SET dt_reservation = ?, statut_reservation = ?, passager_id = ?, client_id = ? WHERE numero = ?");
		
			//date de reservation (date)
			if (reservation.getDtReservation() != null) {
				ps.setDate(1, new java.sql.Date(reservation.getDtReservation().getTime()));
			} else {
				ps.setNull(1, Types.DATE);
			}
			
			//statut (enum)
			if (reservation.getStatut() != null) {
				ps.setString(2, reservation.getStatut().toString());
			} else {
				ps.setNull(2, Types.VARCHAR);
			}
			
			//passager (lien)
			if (reservation.getPassager() != null && reservation.getPassager().getId() != null) {
				ps.setLong(3, reservation.getPassager().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			//client (lien)
			if (reservation.getClient() != null && reservation.getClient().getId() != null) {
				ps.setLong(4, reservation.getClient().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			
			//numero
			ps.setInt(5, reservation.getNumero());

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Integer numero = keys.getInt(1);
					reservation.setNumero(numero);
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
	public void delete(Reservation reservation) {
		deleteById(reservation.getNumero());
	}

	@Override
	public void deleteById(Integer numero) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM reservation WHERE numero = ?");

			ps.setInt(1, numero);

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
