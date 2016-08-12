package com.example.mainui;
/**
 * 选择端口
 */
import java.awt.BorderLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class SelectDialog extends JDialog implements ActionListener{
	
	JPanel jp1,jp2 = null;
	JLabel jl = null;
    JComboBox jcb;
    JButton jb;
    
    String taps[] = {
    	"COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM11"
    };
    public String port = null;
	//owner 表示父窗口，title表示标题，modal指定的模式窗口
	public SelectDialog(JFrame owner, String title, boolean modal) {
		// TODO Auto-generated constructor stub
		super(owner, title, modal);
		
		jp1 = new JPanel();
		jl = new JLabel("请选择端口");
		jcb = new JComboBox(taps);
		jcb.setEditable(true);
		jcb.addActionListener(this);
		
		
		 ItemListener itemListener = new ItemListener() {
		      public void itemStateChanged(ItemEvent itemEvent) {
		        int state = itemEvent.getStateChange();
		        System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
		        System.out.println("Item: " + itemEvent.getItem());
		        ItemSelectable is = itemEvent.getItemSelectable();
		        System.out.println(", Selected: " + selectedString(is));
		        port = selectedString(is);
		        System.out.println(port);
		      }

			private String selectedString(ItemSelectable is) {
				// TODO Auto-generated method stub
				 Object selected[] = is.getSelectedObjects();
				return ((selected.length == 0) ? "null" : (String) selected[0]);
				
			}
		    };
		    jcb.addItemListener(itemListener);
		
		
		
		
		
		jp1.add(jl);
		jp1.add(jcb);
		
		jp2 = new JPanel();
		jb = new JButton("确定");
		jb.addActionListener(this);
		jp2.add(jb);
		
		this.add(jp1);
		this.add(jp2,BorderLayout.SOUTH);
		
		this.setSize(300,200);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jcb){
			 System.out.println("Selected index=" + ((JComboBox)e.getSource()).getItemCount());
		}else if(e.getSource() == jb){
		    try {
				this.dispose();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
}
