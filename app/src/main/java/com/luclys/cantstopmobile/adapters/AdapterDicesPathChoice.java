package com.luclys.cantstopmobile.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;

import com.client.plple.plple.commun.constantes.NET;
import com.luclys.cantstopmobile.Client;
import com.luclys.cantstopmobile.R;

import java.util.ArrayList;


/**
 * Crée l'adapter permettant d'afficher les UE obligatoires (cours tout en bas)
 */
public class AdapterDicesPathChoice extends BaseAdapter {

    private final ArrayList<Integer> listDiceRoll;
    private final ArrayList<Integer> climberPos;
    private final LayoutInflater inflter;
    private final Context context;

    /**
     * @param context      : Contexte de l'application
     * @param listDiceRoll : Liste des valeurs des dès tirés
     */
    public AdapterDicesPathChoice(Context context, ArrayList<Integer> listDiceRoll, ArrayList<Integer> climberPos) {
        this.listDiceRoll = listDiceRoll;
        this.climberPos = climberPos;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }


    /**
     * Longueur de l'adapteur
     *
     * @return nombre de dés.
     */
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Les paramètres suivants sont gérés par android
     *
     * @param i         l'index de la liste
     * @param view      la view
     * @param viewGroup Rempli les champs du layout ligne_ue avec les éléments
     *                  qu'on a passé en paramètre.
     *                  Et modifie dynamiquement la liste des cours dont les
     *                  checkbox ont été cochées.
     * @return : Renvoie la view avec les champs modifiés
     */
    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.dice_and_path_choice, viewGroup, false);

        final Button btn1 = view.findViewById((R.id.btnPathChoice));
        final Button btn2 = view.findViewById(R.id.btnPathChoice2);
        final ImageView dice1 = view.findViewById(R.id.imgDice1);
        final ImageView dice2 = view.findViewById(R.id.imgDice2);
        final ImageView dice3 = view.findViewById(R.id.imgDice3);
        final ImageView dice4 = view.findViewById(R.id.imgDice4);


        ArrayList<Integer> arrayDiceRes = new ArrayList<>();
        arrayDiceRes.add(R.drawable.ic_dice_1);
        arrayDiceRes.add(R.drawable.ic_dice_2);
        arrayDiceRes.add(R.drawable.ic_dice_3);
        arrayDiceRes.add(R.drawable.ic_dice_4);
        arrayDiceRes.add(R.drawable.ic_dice_5);
        arrayDiceRes.add(R.drawable.ic_dice_6);

        final int sumDices1;
        final int sumDices2;
        final int nbFreeClimbers = countElement(0, climberPos);

        switch (i) {
            case 0:
                sumDices1 = listDiceRoll.get(0) + listDiceRoll.get(1);
                sumDices2 = listDiceRoll.get(2) + listDiceRoll.get(3);
                dice1.setImageResource(arrayDiceRes.get(listDiceRoll.get(0)-1));
                dice2.setImageResource(arrayDiceRes.get(listDiceRoll.get(1)-1));
                dice3.setImageResource(arrayDiceRes.get(listDiceRoll.get(2)-1));
                dice4.setImageResource(arrayDiceRes.get(listDiceRoll.get(3)-1));
                break;
            case 1:
                sumDices1 = listDiceRoll.get(3) + listDiceRoll.get(1);
                sumDices2 = listDiceRoll.get(0) + listDiceRoll.get(2);
                dice1.setImageResource(arrayDiceRes.get(listDiceRoll.get(3)-1));
                dice2.setImageResource(arrayDiceRes.get(listDiceRoll.get(1)-1));
                dice3.setImageResource(arrayDiceRes.get(listDiceRoll.get(0)-1));
                dice4.setImageResource(arrayDiceRes.get(listDiceRoll.get(2)-1));
                break;
            case 2:
                sumDices1 = listDiceRoll.get(2) + listDiceRoll.get(1);
                sumDices2 = listDiceRoll.get(0) + listDiceRoll.get(3);
                dice1.setImageResource(arrayDiceRes.get(listDiceRoll.get(2)-1));
                dice2.setImageResource(arrayDiceRes.get(listDiceRoll.get(1)-1));
                dice3.setImageResource(arrayDiceRes.get(listDiceRoll.get(0)-1));
                dice4.setImageResource(arrayDiceRes.get(listDiceRoll.get(3)-1));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + i);
        }

        boolean a1 = climberPos.contains(sumDices1);
        boolean a2 = climberPos.contains(sumDices2);

        if ( (nbFreeClimbers >= 2) || (a1 || a2) ) {
            if (nbFreeClimbers == 0) { // One path available : the one who is True.
                final int trueSum = a1 ? sumDices1 : sumDices2;

                btn1.setText(String.format("Progresser sur %", trueSum));
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Client.getInstance().getmSocket().emit(NET.REQUEST_ROLL_DICES, trueSum);
                    }
                });
            } else { // Both path available
                btn1.setText(String.format("Progresser sur %d et %d", sumDices1, sumDices2));
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Client.getInstance().getmSocket().emit(NET.REQUEST_ROLL_DICES, sumDices1, sumDices2);
                    }
                });
            }
        } else {
            if (nbFreeClimbers == 0){ // 0 path available
                btn1.setEnabled(false);
                btn1.setText(R.string.unavailableChoice);

                //Client.getInstance().getmSocket().emit(NET.PROGRESS_IMPOSSIBLE);

            } else { // 2 paths available : choose 1
                btn2.setVisibility(View.VISIBLE);

                btn1.setText(String.format("Progresser sur %d", sumDices1));
                btn2.setText(String.format("Progresser sur %d", sumDices2));

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Client.getInstance().getmSocket().emit(NET.REQUEST_ROLL_DICES, sumDices1);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Client.getInstance().getmSocket().emit(NET.REQUEST_ROLL_DICES, sumDices2);
                    }
                });


            }
        }

        return view;
    }

    private int countElement(int i, ArrayList<Integer> climberPos) {
        int res = 0;
        for (Integer el : climberPos) {
            if (el == i) {
                res++;
            }
        }
        return res;
    }


}
