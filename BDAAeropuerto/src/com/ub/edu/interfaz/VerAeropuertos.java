package com.ub.edu.interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.annotations.Parent;

import com.ub.edu.bda.accesosHibernate;

import Objetos.aeropuerto;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerAeropuertos extends JFrame {

	private JPanel contentPane;
	private JTable tbl_aeropuertos;
	private JFrame thisframe;
	addAeropuertos addAeropuerto;

	/**
	 * Create the frame.
	 */
	public VerAeropuertos() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 609, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNuevoAeropuerto = new JButton("A\u00F1adir aeropuerto");
		btnNuevoAeropuerto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addAeropuertos addAeropuerto = new addAeropuertos(thisframe, true, false, null);
				addAeropuerto.setVisible(true);
				
				//al volver cargamos la tabla nuevamente por si han habido cambios
				
				buscarAeropuertos();
			}
		});
		
		JLabel lblAeropuertos = new JLabel("Aeropuertos:");
		
		tbl_aeropuertos = new JTable();
		
		JButton btnEditarAeropuerto = new JButton("Editar aeropuerto");
		btnEditarAeropuerto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				 ArrayList<String> result = new ArrayList<String>();
			     for (int i = 0; i < tbl_aeropuertos.getModel().getColumnCount(); i++) {
			         result.add(tbl_aeropuertos.getModel().getValueAt(tbl_aeropuertos.getSelectedRow(), i).toString());
			     }
			     
			     aeropuerto a = new aeropuerto(Integer.valueOf(result.get(0)), result.get(1).toString(), result.get(2).toString(), result.get(3).toString(), Float.valueOf(result.get(4)));
			     
				addAeropuertos addAeropuerto = new addAeropuertos(thisframe, true, true, a);
				addAeropuerto.setVisible(true);
				
				buscarAeropuertos();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(tbl_aeropuertos, GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNuevoAeropuerto, Alignment.TRAILING)
								.addComponent(lblAeropuertos))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEditarAeropuerto)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAeropuertos)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbl_aeropuertos, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevoAeropuerto)
						.addComponent(btnEditarAeropuerto)))
		);
		contentPane.setLayout(gl_contentPane);
		
		thisframe = this;
		
		buscarAeropuertos();
	}
	
	private void buscarAeropuertos(){
		accesosHibernate h = new accesosHibernate();
		
		@SuppressWarnings("unchecked")
		List <aeropuerto> aeropuertos = h.select("SELECT * FROM aeropuerto").addEntity(aeropuerto.class).list();
		
		rellenarTablaAeropuertos(aeropuertos);
	}
	
	private void rellenarTablaAeropuertos(List <aeropuerto> aeropuertos){
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("Nombre"); 
		model.addColumn("Ciudad");
		model.addColumn("Codigo internacional");
		model.addColumn("Coste del handling"); 
		
		tbl_aeropuertos.setModel(model);
		
		if(aeropuertos!=null){
			for(aeropuerto a: aeropuertos)
				model.addRow(new Object[]{a.getId_aeropuerto(), a.getNombre(), a.getCiudad(), a.getcodigoInternacional(), a.getCosteDelHandling()});
		}
	}
}
