package com.example.javac;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class NewLineFilter extends DocumentFilter
{
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
        throws BadLocationException
    {
        if ("\n".equals(str))
            str = addWhiteSpace(fb.getDocument(), offs);

        super.insertString(fb, offs, str, a);
    }

    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
        throws BadLocationException
    {
        if ("\n".equals(str))
            str = addWhiteSpace(fb.getDocument(), offs);

        super.replace(fb, offs, length, str, a);
    }

    private String addWhiteSpace(Document doc, int offset)
        throws BadLocationException
    {
        StringBuilder whiteSpace = new StringBuilder("\n");
        Element rootElement = doc.getDefaultRootElement();
        int line = rootElement.getElementIndex( offset );
        int i = rootElement.getElement(line).getStartOffset();

        while (true)
        {
            String temp = doc.getText(i, 1);

            if (temp.equals(" ") || temp.equals("\t"))
            {
                whiteSpace.append(temp);
                i++;
            }
            else
                break;
        }

        return whiteSpace.toString();
    }

    private static void createAndShowUI()
    {
        JTextArea textArea = new JTextArea(5, 50);
        AbstractDocument doc = (AbstractDocument)textArea.getDocument();
        doc.setDocumentFilter( new NewLineFilter() );

        JFrame frame = new JFrame("NewLineFilter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add( new JScrollPane(textArea) );
        frame.pack();
        frame.setLocationByPlatform( true );
        frame.setVisible( true );
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowUI();
            }
        });
    }
}