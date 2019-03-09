/*
Muhammet METİN
15010011029
Dijkstra en kısa yol algoritması
*/
package com.example.mmetin.shortestpath;

import android.annotation.SuppressLint;
import android.content.DialogInterface;

import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainLinear;
    private TextView txInfoCoor;
    private int kacButtonSayac=0;
    private String cizilecekNodelar="";
    private int cizilecekNodelarSayac=0;
    private DrawView drawView;
    private ArrayList<Button> cizilecekIkiButton =new ArrayList<>();
    private ArrayList<Button> nodes =new ArrayList<>();
    private Button btnBirlestir;
    private int graf[][];
    private int agirlik=0;
    private String destinationButtonId="";
    private int isSelectedTwoNode=0;
    private int startVertex,endVertex;
    LinearLayout.LayoutParams lp_= new LinearLayout.LayoutParams(100,100,100.0f);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer node = MediaPlayer.create(this, R.raw.node);
        final MediaPlayer birlestir = MediaPlayer.create(this, R.raw.birlestir);
        final MediaPlayer blob = MediaPlayer.create(this, R.raw.blob);

        btnBirlestir=(Button)findViewById(R.id.btnNodeCiz);
        mainLinear=(RelativeLayout)findViewById(R.id.linearMain);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100,100,100.0f);
        mainLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){

                    final Button myButton = new Button(MainActivity.this);
                    nodes.add(myButton);
                    blob.start();
                    kacButtonSayac++;
                    myButton.setId(kacButtonSayac);
                    myButton.setText(String.valueOf(kacButtonSayac));
                    myButton.setX(event.getX());
                    myButton.setY(event.getY());
                    myButton.setBackground(getResources().getDrawable(R.drawable.circle_button));
                    myButton.setLayoutParams(lp);
                    mainLinear.addView(myButton, lp);
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            birlestir.start();
                            if(cizilecekNodelarSayac<2) {
                                cizilecekNodelar += "-" + String.valueOf(v.getId());
                                Toast.makeText(MainActivity.this, "Tiklanci" + cizilecekNodelar, Toast.LENGTH_SHORT).show();
                                cizilecekIkiButton.add(myButton);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Shortes Path");
                                builder.setMessage("Aynı anda iki tane node işaretleyebilirsiniz");
                                builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        cizilecekNodelar="";
                                        cizilecekNodelarSayac=0;
                                    }
                                });
                                builder.show();
                            }

                            cizilecekNodelarSayac++;
                        }
                    });
                    myButton.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if(isSelectedTwoNode<2){
                                destinationButtonId+=String.valueOf(v.getId()-1)+"-";
                                if(isSelectedTwoNode==0)
                                {
                                    Toast.makeText(MainActivity.this,String.valueOf(isSelectedTwoNode+1)+". buton seçildi",Toast.LENGTH_SHORT).show();
                                }else if(isSelectedTwoNode==1){
                                    Toast.makeText(MainActivity.this,String.valueOf(isSelectedTwoNode+1)+". buton seçildi",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this,"Seçimleriniz iptal edildi tekrar seçiniz.",Toast.LENGTH_SHORT).show();
                                }
                                isSelectedTwoNode++;
                            }

                            return true;
                        }
                    });

                }
                //node sayısı büyüklüğünde graf oluşturuldu ve sıfılandı
                graf=new int[kacButtonSayac][kacButtonSayac];
                for (int i = 0 ; i < kacButtonSayac ; i++){
                    for (int j = 0 ; j < kacButtonSayac ; j++){
                        graf[i][j]=0;
                    }
                }
                return false;
            }
        });
        //nodelar birleştiriliyor
        btnBirlestir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                node.start();
                DrawView dv = new DrawView(MainActivity.this,Math.round(cizilecekIkiButton.get(0).getX())+50,Math.round(cizilecekIkiButton.get(0).getY())+50,Math.round(cizilecekIkiButton.get(1).getX())+50,Math.round(cizilecekIkiButton.get(1).getY())+50,true);

                mainLinear.addView(dv);
                cizilecekNodelar="";
                cizilecekNodelarSayac=0;
                //bu kısmın amacı aradaki çizgi butonun üzerinde görünüyordu
                //ve hoş görünmüyordu bunun geçici çözümü için(buton ağırlığı ayarlanamadı)
                ArrayList<Button> temp=cizilecekIkiButton;
                ViewGroup layout = (ViewGroup) cizilecekIkiButton.get(0).getParent();
                layout.removeView(cizilecekIkiButton.get(0));
                mainLinear.addView(temp.get(0),lp);
                ViewGroup layout2 = (ViewGroup) cizilecekIkiButton.get(0).getParent();
                layout2.removeView(cizilecekIkiButton.get(1));
                mainLinear.addView(temp.get(1),lp);


                //başlangıç grafı oluşturuluyor
                double xFarkKare=Math.pow(Math.abs(Math.round(cizilecekIkiButton.get(0).getX()-cizilecekIkiButton.get(1).getX())),2);
                double yfarkKare=Math.pow(Math.abs(Math.round(cizilecekIkiButton.get(0).getY()-cizilecekIkiButton.get(1).getY())),2);
                agirlik=(int)Math.round(Math.sqrt(xFarkKare+yfarkKare)/10);
                Toast.makeText(MainActivity.this,String.valueOf(agirlik),Toast.LENGTH_SHORT).show();
                graf[cizilecekIkiButton.get(0).getId()-1][cizilecekIkiButton.get(1).getId()-1]=agirlik;
                graf[cizilecekIkiButton.get(1).getId()-1][cizilecekIkiButton.get(0).getId()-1]=agirlik;
                cizilecekIkiButton.clear();
            }
        });
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
    public void hesaplaClicked(View view){
        //ShortestPath t = new ShortestPath();
        //t.dijkstra(graf, 0);
        if(destinationButtonId==""){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Shortes Path");
            builder.setMessage("Başlangıç ve bitiş nodelarını seçmediniz lütfen noda basılı tutarak seçiniz");
                                /*builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id) {
                                        cizilecekNodelar="";
                                    }
                                });*/
            builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();
        }else{
            DijkstrasAlgorithm dj = new DijkstrasAlgorithm();
            String []dest=destinationButtonId.split("-");
            startVertex=Integer.parseInt(dest[0]);
            endVertex=Integer.parseInt(dest[1]);
            String s =dj.dijkstra(graf,startVertex);
            String[] x=s.split(":");
            String []paths=new String[x.length];
            ArrayList<String>maliyetler = new ArrayList<>();
            for (int m = 0 ; m < x.length ; m++) {
                String[] b = x[m].split("__");
                maliyetler.add(b[1]);
                paths[m] = b[0];
            }
                String []path=new String[kacButtonSayac];

                ArrayList<Button> tempNode=new ArrayList<>();
                for (int i = 0 ; i < paths.length ; i++){
                    path=paths[i].split("-");
                    if(Integer.parseInt(path[path.length-1])==endVertex){
                        //System.out.println("path::::"+path.toString());
                        for (int j = 0 ; j < path.length-1; j++){
                            nodes.get(Integer.parseInt(path[j]));
                            nodes.get(Integer.parseInt(path[j+1]));
                            tempNode=nodes;

                            DrawView dv = new DrawView(MainActivity.this,Math.round(nodes.get(Integer.parseInt(path[j])).getX())+50,Math.round(nodes.get(Integer.parseInt(path[j])).getY())+50,Math.round(nodes.get(Integer.parseInt(path[j+1])).getX())+50,Math.round(nodes.get(Integer.parseInt(path[j+1])).getY())+50,false);
                            mainLinear.addView(dv);
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Shortes Path");
                        builder.setMessage("En kısa yol :: "+String.valueOf(Integer.parseInt(path[0])+1)+" --> "+String.valueOf(Integer.parseInt(path[path.length-1])+1)+". Maliyes ise :: "+maliyetler.get(i));

                        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        builder.show();
                    }
                }




        }

        for (int i = 0; i < kacButtonSayac ; i++){
            for (int j = 0; j < kacButtonSayac ; j++){
                System.out.print(" "+graf[i][j]);
            }
            System.out.println();
        }

    }

}
