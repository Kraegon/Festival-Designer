package timetableModule.gui;






import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import timetableModule.data.Artist;






public class ArtistPanel{
	
	public JPanel makeArtistPane(JFrame frame){
		JPanel artistPane = new JPanel(new BorderLayout());
		final JComponent[] comps = new JComponent[]{
				new JLabel("Name: "),new JLabel ("Genre: "),new JLabel ("Misc: "),
				new JTextField("name"),	new JTextField("genre"), new JTextField("misc")
		};
		JPanel leftPane = new JPanel(new GridLayout(3,1));
		JPanel rightPane = new JPanel(new GridLayout(3,1));
		for(JComponent component : comps){
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}	
		JPanel okPane = new JPanel(new FlowLayout());
		JButton ok = new JButton("OK");
		ok.addActionListener(new InputListener(frame, comps, "artist"));
		okPane.add(ok);
		artistPane.add(leftPane, BorderLayout.WEST);
		artistPane.add(rightPane, BorderLayout.CENTER);
		artistPane.add(okPane, BorderLayout.SOUTH);
		return artistPane;
	}
	
	public JPanel editArtistPane(final JFrame frame, String targetArtist){
		JPanel artistPane = new JPanel(new BorderLayout());
		final Artist artist = IO.getInstance().getFestival().findArtist(targetArtist);
		final JComponent[] comps = new JComponent[]{
				new JLabel("Name: "),new JLabel ("Genre: "),new JLabel ("Misc: "),
				new JTextField(artist.getName()),	new JTextField(artist.getGenre()), new JTextField(artist.getMisc())
		};
		JPanel leftPane = new JPanel(new GridLayout(3,1));
		JPanel rightPane = new JPanel(new GridLayout(3,1));

		for(JComponent component : comps){
			if(component.getClass() == JLabel.class){
				leftPane.add(component);
			} else {
				rightPane.add(component);
			}
		}	
		JPanel okPane = new JPanel(new FlowLayout());
		JButton ok = new JButton("OK");
		ok.addActionListener(new EditListener(frame, comps, artist));
		okPane.add(ok);
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IO.getInstance().getFestival().getArtists().remove(artist);
				frame.dispose();
			}
		});
		okPane.add(delete);
		artistPane.add(leftPane, BorderLayout.WEST);
		artistPane.add(rightPane, BorderLayout.CENTER);
		artistPane.add(okPane, BorderLayout.SOUTH);
		return artistPane;
	}
}
