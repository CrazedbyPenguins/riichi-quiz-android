package pw.cbp.riichimahjongscoringquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private SharedPreferences sharedPreferences;
    private final String MORE_SMALL_HANDS_KEY = "moreSmallHands";
    private final String SHOW_STATISTICS_KEY = "showStatistics";

    private final String NUM_HANDS_KEY = "numHands";
    private final String HAND_TILES_KEY = "handTiles";

    ColorStateList defaultTextViewTextColor;
    ColorStateList defaultEditTextTextColor;
    ColorStateList greenColorStateList = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.GREEN});
    ColorStateList redColorStateList = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.RED});
    LinearLayout.LayoutParams linearLayoutLayoutParams;
    Random rand = new Random();

    LinearLayout doraIndicatorsLinearLayout;
    LinearLayout uraDoraIndicatorsLinearLayout;
    ConstraintLayout statisticsConstraintLayout;
    LinearLayout handClosedLinearLayout;
    ConstraintLayout handOpenConstraintLayout;
    LinearLayout handOpenLinearLayout;
    ConstraintLayout handTsumoConstraintLayout;
    LinearLayout handTsumoLinearLayout;
    ConstraintLayout handRonConstraintLayout;
    TextView handRonWindTextView;
    LinearLayout handRonLinearLayout;
    CheckBox yakumanAnswerCheckBox;

    protected int[] tileImages;
    int numHands = 0;
    int numCorFu = 0;
    int numCorHan = 0;
    int numCorPay = 0;
    int roundWind;
    int seatWind;
    int ronWind;
    int waitType;
    boolean isRiichi;
    boolean isClosed;
    boolean isYakuman;
    boolean isDebugMode = false;
    boolean isDebugYakuman = false;
    int handFu;
    int handHan;
    int handPayKo;
    int handPayOya;
    int numDora;
    int numClosedTiles;
    int numOpenTiles;
    int curOpenTile;
    int numKans;
    int agariHai;
    String answerString;
    String answerString2;
    boolean isRyanmen;
    boolean isChecked;
    int numYakuman;
    int unroundedFu;
    int basePay;
    int honItsu;
    boolean isMeldPossible;
    boolean isJantouSet;
    int curMeld;
    int numMelds;
    int forceKans;
    int forceAnKous;
    int forceMinKous;
    int sanityCheck = 0;
    int meldPointer;
    boolean isDebugSpecific = false;
    boolean isMassDebug = false;
    int debugPointer = 0;
    boolean isError;
    boolean isInfure = true;
    int totalHan;
    int[] meldTile = new int[5];
    int[] meldType = new int[5];
    int[] meldClose = new int[5];
    boolean[] isMeldChecked = new boolean[5];
    int[] handTiles = new int[34];
    int[] handClosedTiles = new int[34];
    int[] handOpenTiles = new int[34];
    int[] handOpenDisp = new int[16];
    int[] closedKans = new int[4];
    int[] doraTiles = new int[8];
    int[] minMelds = new int[19];
    String[] yakuStrings = new String[] {
            "Riichi", "Tsumo", "Round wind", "Seat wind", "Yakuhai", "Tanyao", "Pinfu", "Iipeikou", "Chanta", "Ittsu",
            "Sanshoku", "Sanshoko doukou", "Open riichi" /*house rule*/, "San kantsu", "Toitoi", "San ankou", "Shousangen", "Honroutou", "Chiitoitsu", "Junchan",
            "Honitsu",  "Ryanpeikou", "Chinitsu"
    };
    String[] yakumanStrings = new String[] {
            "Daisangen", "Suu ankou", "Suu ankou tanki wait", "Tsuuiisou", "Ryuuiisou", "Daisharin" /*house rule*/, "Chinroutou", "Kokushi musou", "Kokushi musou 13-sided wait", "Shousuushi",
            "Daisuushi", "Suu kantsu", "Chuuren poutou", "Junsei chuuren poutou", "Open riichi, win by Ron"
    };
    boolean[] handYaku = new boolean[23];
    boolean[] compatibleYaku = new boolean[23];
    boolean[] legalTiles = new boolean[34];
    int arrayPointer;
    TextView roundField;
    TextView seatField;
    TextView riichiField;
    EditText fuEntry;
    EditText fuAnswer;
    EditText hanEntry;
    EditText hanAnswer;
    CheckBox yakumanBox;
    EditText koEntry;
    EditText koAnswer;
    EditText oyaEntry;
    EditText oyaAnswer;
    Button submitButton;
    /*JButton dealButton;*/
    TextView corField;
    TextView corFuField;
    TextView corHanField;
    TextView corPayField;
    TextView fuList;
    TextView hanList;
    /*JCheckBox infureBox;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        doraIndicatorsLinearLayout = findViewById(R.id.doraIndicatorsLinearLayout);
        uraDoraIndicatorsLinearLayout = findViewById(R.id.uraDoraIndicatorsLinearLayout);
        statisticsConstraintLayout = findViewById(R.id.statisticsConstraintLayout);
        handClosedLinearLayout = findViewById(R.id.handClosedLinearLayout);
        handOpenConstraintLayout = findViewById(R.id.handOpenConstraintLayout);
        handOpenLinearLayout = findViewById(R.id.handOpenLinearLayout);
        handTsumoConstraintLayout = findViewById(R.id.handTsumoConstraintLayout);
        handTsumoLinearLayout = findViewById(R.id.handTsumoLinearLayout);
        handRonConstraintLayout = findViewById(R.id.handRonConstraintLayout);
        handRonWindTextView = findViewById(R.id.handRonWindTextView);
        handRonLinearLayout = findViewById(R.id.handRonLinearLayout);
        yakumanAnswerCheckBox = findViewById(R.id.yakumanAnswerCheckBox);

        roundField = findViewById(R.id.roundWindTextView);
        seatField = findViewById(R.id.seatWindTextView);
        riichiField = findViewById(R.id.riichiTextView);
        fuEntry = findViewById(R.id.fuEditText);
        fuAnswer = findViewById(R.id.fuAnswerEditText);
        hanEntry = findViewById(R.id.hanEditText);
        hanAnswer = findViewById(R.id.hanAnswerEditText);
        yakumanBox = findViewById(R.id.yakumanCheckBox);
        koEntry = findViewById(R.id.nonDealerEditText);
        koAnswer = findViewById(R.id.nonDealerAnswerEditText);
        oyaEntry = findViewById(R.id.dealerEditText);
        oyaAnswer = findViewById(R.id.dealerAnswerEditText);
        submitButton = findViewById(R.id.submitButton);
        corField = findViewById(R.id.totalHandsTextView);
        corFuField = findViewById(R.id.correctFuTextView);
        corHanField = findViewById(R.id.correctHanTextView);
        corPayField = findViewById(R.id.correctPaymentsTextView);
        fuList = findViewById(R.id.fuCalculationTextView);
        hanList = findViewById(R.id.hanCalculationTextView);

        defaultTextViewTextColor = hanList.getTextColors();
        defaultEditTextTextColor = fuEntry.getTextColors();

        linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutLayoutParams.setMarginStart((int)Math.ceil(getResources().getDisplayMetrics().density * 2));

        this.tileImages = new int[] {
                R.drawable.man1, R.drawable.man2, R.drawable.man3, R.drawable.man4, R.drawable.man5, R.drawable.man6, R.drawable.man7, R.drawable.man8, R.drawable.man9,
                R.drawable.pin1, R.drawable.pin2, R.drawable.pin3, R.drawable.pin4, R.drawable.pin5, R.drawable.pin6, R.drawable.pin7, R.drawable.pin8, R.drawable.pin9,
                R.drawable.sou1, R.drawable.sou2, R.drawable.sou3, R.drawable.sou4, R.drawable.sou5, R.drawable.sou6, R.drawable.sou7, R.drawable.sou8, R.drawable.sou9,
                R.drawable.haku, R.drawable.hatsu, R.drawable.chun, R.drawable.ton, R.drawable.nan, R.drawable.sha, R.drawable.pei, R.drawable.back
        };

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        isInfure = sharedPreferences.getBoolean(MORE_SMALL_HANDS_KEY, true);
        statisticsConstraintLayout.setVisibility(sharedPreferences.getInt(SHOW_STATISTICS_KEY, View.GONE));

        newHand();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        restoreHand(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(MORE_SMALL_HANDS_KEY, isInfure);
        sharedPreferencesEditor.putInt(SHOW_STATISTICS_KEY, statisticsConstraintLayout.getVisibility());
        sharedPreferencesEditor.apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(NUM_HANDS_KEY, numHands);
        outState.putIntArray(HAND_TILES_KEY, handTiles);
    }

    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu);
        popupMenu.getMenu().findItem(R.id.moreSmallHandsMenuItem).setChecked(!isInfure);
        popupMenu.getMenu().findItem(R.id.showStatisticsMenuItem).setChecked(statisticsConstraintLayout.getVisibility() == View.VISIBLE);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.moreSmallHandsMenuItem:
                isInfure = !isInfure;
                menuItem.setChecked(!isInfure);

                keepMenuOpen(menuItem);
                return false;
            case R.id.showStatisticsMenuItem:
                statisticsConstraintLayout.setVisibility(statisticsConstraintLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                menuItem.setChecked(statisticsConstraintLayout.getVisibility() == View.VISIBLE);

                keepMenuOpen(menuItem);
                return false;
            default:
                return false;
        }
    }

    public void keepMenuOpen(MenuItem menuItem) {
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menuItem.setActionView(new View(this));
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });
    }

    public void onYakumanCheckBoxClick(View view) {
        if(this.yakumanBox.isChecked()) {
            this.fuEntry.setEnabled(false);
            this.fuEntry.setText("0");
            this.hanEntry.setEnabled(false);
            this.hanEntry.setText("0");
        } else {
            this.fuEntry.setEnabled(true);
            fuEntry.setText("");
            this.hanEntry.setEnabled(true);
            hanEntry.setText("");
        }
    }

    public void repaint() {
        ImageView imageView;
        LinearLayout handWinningLinearLayout;

        this.numClosedTiles = 0;

        int i;
        for(i = 0; i < this.handClosedTiles.length; ++i) {
            this.numClosedTiles += this.handClosedTiles[i];
        }

        for(i = 0; i < this.closedKans.length; ++i) {
            if(this.closedKans[i] > -1) {
                this.numClosedTiles += 4;
            }
        }

        handClosedLinearLayout.removeAllViews();

        for(i = 0; i < this.handClosedTiles.length; ++i) {
            if(this.handClosedTiles[i] > 0) {
                for(int j = 0; j < this.handClosedTiles[i]; ++j) {
                    imageView = new ImageView(this);
                    imageView.setImageResource(tileImages[i]);
                    handClosedLinearLayout.addView(imageView, linearLayoutLayoutParams);
                }
            }
        }

        for(i = 0; i < this.closedKans.length; ++i) {
            if(this.closedKans[i] > -1) {
                imageView = new ImageView(this);
                imageView.setImageResource(tileImages[34]);
                handClosedLinearLayout.addView(imageView, linearLayoutLayoutParams);

                imageView = new ImageView(this);
                imageView.setImageResource(tileImages[this.closedKans[i]]);
                handClosedLinearLayout.addView(imageView, linearLayoutLayoutParams);

                imageView = new ImageView(this);
                imageView.setImageResource(tileImages[this.closedKans[i]]);
                handClosedLinearLayout.addView(imageView, linearLayoutLayoutParams);

                imageView = new ImageView(this);
                imageView.setImageResource(tileImages[34]);
                handClosedLinearLayout.addView(imageView, linearLayoutLayoutParams);
            }
        }

        if(this.numOpenTiles > 0) {
            handOpenConstraintLayout.setVisibility(View.VISIBLE);
            handOpenLinearLayout.removeAllViews();

            for(i = 0; i < this.numOpenTiles; ++i) {
                imageView = new ImageView(this);
                imageView.setImageResource(tileImages[this.handOpenDisp[i]]);
                handOpenLinearLayout.addView(imageView, linearLayoutLayoutParams);
            }
        }
        else
            handOpenConstraintLayout.setVisibility(View.GONE);

        if(this.ronWind == 4) {
            handTsumoConstraintLayout.setVisibility(View.VISIBLE);
            handRonConstraintLayout.setVisibility(View.GONE);
            handWinningLinearLayout = handTsumoLinearLayout;
        } else {
            handTsumoConstraintLayout.setVisibility(View.GONE);
            handRonConstraintLayout.setVisibility(View.VISIBLE);
            handWinningLinearLayout = handRonLinearLayout;

            handRonWindTextView.setText(this.getWindString(this.ronWind));
        }

        handWinningLinearLayout.removeAllViews();

        imageView = new ImageView(this);
        imageView.setImageResource(tileImages[this.agariHai]);
        handWinningLinearLayout.addView(imageView, linearLayoutLayoutParams);

        doraIndicatorsLinearLayout.removeAllViews();

        for(i = 0; i < 4; ++i) {
            imageView = new ImageView(this);
            if(this.doraTiles[i] > -1)
                imageView.setImageResource(tileImages[this.doraTiles[i]]);
            else
                imageView.setImageResource(tileImages[34]);
            doraIndicatorsLinearLayout.addView(imageView, linearLayoutLayoutParams);
        }

        uraDoraIndicatorsLinearLayout.removeAllViews();

        for(i = 4; i < 8; ++i) {
            imageView = new ImageView(this);
            if(this.doraTiles[i] > -1)
                imageView.setImageResource(tileImages[this.doraTiles[i]]);
            else
                imageView.setImageResource(tileImages[34]);
            uraDoraIndicatorsLinearLayout.addView(imageView, linearLayoutLayoutParams);
        }
    }

    public void newHand() {
        submitButton.setEnabled(true);
        numHands++;

        this.fuEntry.setText("");
        this.fuEntry.setTextColor(defaultEditTextTextColor);
        this.fuEntry.setBackgroundTintList(null);
        this.hanEntry.setText("");
        this.hanEntry.setTextColor(defaultEditTextTextColor);
        this.hanEntry.setBackgroundTintList(null);
        this.fuEntry.setEnabled(true);
        this.hanEntry.setEnabled(true);
        this.koEntry.setText("");
        this.koEntry.setTextColor(defaultEditTextTextColor);
        this.koEntry.setBackgroundTintList(null);
        this.oyaEntry.setText("");
        this.oyaEntry.setTextColor(defaultEditTextTextColor);
        this.oyaEntry.setBackgroundTintList(null);
        this.yakumanBox.setChecked(false);
        this.yakumanBox.setButtonTintList(null);
        this.fuAnswer.setText("");
        this.hanAnswer.setText("");
        this.koAnswer.setText("");
        this.oyaAnswer.setText("");
        yakumanAnswerCheckBox.setChecked(false);
        this.riichiField.setText("");
        this.fuList.setText("");
        this.hanList.setText("");
        this.hanList.setTextColor(defaultTextViewTextColor);
        this.answerString = "";
        this.answerString2 = "";
        this.corField.setText(Integer.toString(this.numHands));
        this.corFuField.setText(Integer.toString(this.numCorFu));
        this.corHanField.setText(Integer.toString(this.numCorHan));
        this.corPayField.setText(Integer.toString(this.numCorPay));

        int i;
        for(i = 0; i < this.handTiles.length; ++i) {
            this.handTiles[i] = 0;
        }

        for(i = 0; i < this.handOpenTiles.length; ++i) {
            this.handOpenTiles[i] = 0;
        }

        for(i = 0; i < this.handOpenDisp.length; ++i) {
            this.handOpenDisp[i] = -1;
        }

        for(i = 0; i < this.handClosedTiles.length; ++i) {
            this.handClosedTiles[i] = 0;
        }

        for(i = 0; i < this.doraTiles.length; ++i) {
            this.doraTiles[i] = -1;
        }

        for(i = 0; i < this.closedKans.length; ++i) {
            this.closedKans[i] = -1;
        }

        for(i = 0; i < this.minMelds.length; ++i) {
            this.minMelds[i] = 0;
        }

        for(i = 0; i < this.meldTile.length; ++i) {
            this.meldTile[i] = 0;
        }

        for(i = 0; i < this.meldType.length; ++i) {
            this.meldType[i] = 3;
        }

        for(i = 0; i < this.meldClose.length; ++i) {
            this.meldClose[i] = 0;
        }

        for(i = 0; i < this.isMeldChecked.length; ++i) {
            this.isMeldChecked[i] = false;
        }

        for(i = 0; i < this.handYaku.length; ++i) {
            this.handYaku[i] = false;
        }

        for(i = 0; i < this.compatibleYaku.length; ++i) {
            this.compatibleYaku[i] = true;
        }

        for(i = 0; i < this.legalTiles.length; ++i) {
            this.legalTiles[i] = true;
        }

        this.honItsu = -1;
        this.agariHai = -1;
        this.curOpenTile = 0;
        this.numKans = 0;
        this.waitType = 0;
        this.isClosed = false;
        this.isRiichi = false;
        this.isRyanmen = false;
        this.curMeld = 4;
        this.numMelds = 0;
        this.isJantouSet = false;
        this.forceKans = 0;
        this.forceAnKous = 0;
        this.forceMinKous = 0;
        this.meldPointer = 0;
        this.numYakuman = 0;
        this.unroundedFu = 0;
        this.handHan = this.handFu = this.handPayKo = this.handPayOya = 0;
        this.numDora = 0;
        this.basePay = 0;
        this.isChecked = false;
        this.isError = false;
        if(this.rand.nextInt(50) == 1) {
            this.roundWind = 3;
        } else if(this.rand.nextInt(15) == 1) {
            this.roundWind = 2;
        } else if(this.rand.nextInt(2) == 1) {
            this.roundWind = 1;
        } else {
            this.roundWind = 0;
        }

        this.seatWind = this.rand.nextInt(4);
        this.ronWind = this.seatWind;
        this.roundField.setText(this.getWindString(this.roundWind));
        this.seatField.setText(this.getWindString(this.seatWind));
        if(this.isDebugMode) {
            this.isDebugMode = false;
            if(this.isDebugYakuman) {
                if(!this.isMassDebug) {
                    this.isDebugYakuman = false;
                }

                this.addYakuman(this.debugPointer);
            } else if(this.isDebugSpecific) {
                this.isDebugSpecific = false;
                this.addYakuman(1337);
            } else {
                this.addYaku(this.debugPointer);
            }
        } else if(this.isInfure) {
            if(this.rand.nextInt(10) == 1) {
                this.addYakuman(this.rand.nextInt(15));
            } else {
                this.addYaku(this.rand.nextInt(23));
                this.addYaku(15);
            }
        } else {
            this.compatibleYaku[17] = this.compatibleYaku[16] = this.compatibleYaku[22] = this.compatibleYaku[12] = false;

            do {
                this.arrayPointer = this.rand.nextInt(23);
            } while(!this.compatibleYaku[this.arrayPointer]);

            this.addYaku(this.arrayPointer);
        }

        if(this.isRiichi) {
            this.riichiField.setText("Riichi declared");
        }

        this.addHandTiles();
        this.setWaits();
        this.numOpenTiles = 0;

        for(i = 0; i < this.handOpenTiles.length; ++i) {
            this.numOpenTiles += this.handOpenTiles[i];
        }

        if(this.numOpenTiles == 0) {
            this.isClosed = true;
        } else {
            this.isClosed = false;
        }

        if(!this.isClosed && this.rand.nextInt(3) == 1) {
            this.ronWind = 4;
        }

        if(this.ronWind == this.seatWind) {
            do {
                this.ronWind = this.rand.nextInt(4);
            } while(this.ronWind == this.seatWind);
        }

        this.addDora();
        this.countYakuman();
        this.isYakuman = this.numYakuman > 0;

        for(i = 0; i < this.handYaku.length; ++i) {
            if(i == 12) {
                ++i;
            }

            this.handYaku[i] = false;
        }

        if(!this.isYakuman) {
            this.countYaku();
            this.countHan();
            this.totalHan += this.handHan;
            this.countFu();
        }

        this.countPayments();
        this.arrayPointer = 0;
        repaint();
    }

    public void newHand(View view) {
        newHand();
    }

    public void restoreHand(Bundle savedInstanceState) {
        submitButton.setEnabled(true);
        // restore numHands

        // @TODO Figure out if I even need this section
        this.fuEntry.setText("");
        this.fuEntry.setTextColor(defaultEditTextTextColor);
        this.fuEntry.setBackgroundTintList(null);
        this.hanEntry.setText("");
        this.hanEntry.setTextColor(defaultEditTextTextColor);
        this.hanEntry.setBackgroundTintList(null);
        this.fuEntry.setEnabled(true);
        this.hanEntry.setEnabled(true);
        this.koEntry.setText("");
        this.koEntry.setTextColor(defaultEditTextTextColor);
        this.koEntry.setBackgroundTintList(null);
        this.oyaEntry.setText("");
        this.oyaEntry.setTextColor(defaultEditTextTextColor);
        this.oyaEntry.setBackgroundTintList(null);
        this.yakumanBox.setChecked(false);
        this.yakumanBox.setButtonTintList(null);
        this.fuAnswer.setText("");
        this.hanAnswer.setText("");
        this.koAnswer.setText("");
        this.oyaAnswer.setText("");
        yakumanAnswerCheckBox.setChecked(false);
        this.riichiField.setText("");
        this.fuList.setText("");
        this.hanList.setText("");
        this.hanList.setTextColor(defaultTextViewTextColor);
        this.answerString = "";
        this.answerString2 = "";
        this.corField.setText(Integer.toString(this.numHands));
        this.corFuField.setText(Integer.toString(this.numCorFu));
        this.corHanField.setText(Integer.toString(this.numCorHan));
        this.corPayField.setText(Integer.toString(this.numCorPay));

        int i;
        for(i = 0; i < this.handTiles.length; ++i) {
            this.handTiles[i] = 0;
        }

        for(i = 0; i < this.handOpenTiles.length; ++i) {
            this.handOpenTiles[i] = 0;
        }

        for(i = 0; i < this.handOpenDisp.length; ++i) {
            this.handOpenDisp[i] = -1;
        }

        for(i = 0; i < this.handClosedTiles.length; ++i) {
            this.handClosedTiles[i] = 0;
        }

        for(i = 0; i < this.doraTiles.length; ++i) {
            this.doraTiles[i] = -1;
        }

        for(i = 0; i < this.closedKans.length; ++i) {
            this.closedKans[i] = -1;
        }

        for(i = 0; i < this.minMelds.length; ++i) {
            this.minMelds[i] = 0;
        }

        for(i = 0; i < this.meldTile.length; ++i) {
            this.meldTile[i] = 0;
        }

        for(i = 0; i < this.meldType.length; ++i) {
            this.meldType[i] = 3;
        }

        for(i = 0; i < this.meldClose.length; ++i) {
            this.meldClose[i] = 0;
        }

        for(i = 0; i < this.isMeldChecked.length; ++i) {
            this.isMeldChecked[i] = false;
        }

        for(i = 0; i < this.handYaku.length; ++i) {
            this.handYaku[i] = false;
        }

        for(i = 0; i < this.compatibleYaku.length; ++i) {
            this.compatibleYaku[i] = true;
        }

        for(i = 0; i < this.legalTiles.length; ++i) {
            this.legalTiles[i] = true;
        }

        this.honItsu = -1;
        this.agariHai = -1;
        this.curOpenTile = 0;
        this.numKans = 0;
        this.waitType = 0;
        this.isClosed = false;
        this.isRiichi = false;
        this.isRyanmen = false;
        this.curMeld = 4;
        this.numMelds = 0;
        this.isJantouSet = false;
        this.forceKans = 0;
        this.forceAnKous = 0;
        this.forceMinKous = 0;
        this.meldPointer = 0;
        this.numYakuman = 0;
        this.unroundedFu = 0;
        this.handHan = this.handFu = this.handPayKo = this.handPayOya = 0;
        this.numDora = 0;
        this.basePay = 0;
        this.isChecked = false;
        this.isError = false;
        // restore roundWind

        // restore seatWind
        this.ronWind = this.seatWind;
        this.roundField.setText(this.getWindString(this.roundWind));
        this.seatField.setText(this.getWindString(this.seatWind));
        if(this.isDebugMode) {
            this.isDebugMode = false;
            if(this.isDebugYakuman) {
                if(!this.isMassDebug) {
                    this.isDebugYakuman = false;
                }

                this.addYakuman(this.debugPointer);
            } else if(this.isDebugSpecific) {
                this.isDebugSpecific = false;
                this.addYakuman(1337);
            } else {
                this.addYaku(this.debugPointer);
            }
        } else if(this.isInfure) {
            if(this.rand.nextInt(10) == 1) {
                this.addYakuman(this.rand.nextInt(15));
            } else {
                this.addYaku(this.rand.nextInt(23));
                this.addYaku(15);
            }
        } else {
            this.compatibleYaku[17] = this.compatibleYaku[16] = this.compatibleYaku[22] = this.compatibleYaku[12] = false;

            do {
                this.arrayPointer = this.rand.nextInt(23);
            } while(!this.compatibleYaku[this.arrayPointer]);

            this.addYaku(this.arrayPointer);
        }

        if(this.isRiichi) {
            this.riichiField.setText("Riichi declared");
        }

        this.addHandTiles();
        this.setWaits();
        this.numOpenTiles = 0;

        for(i = 0; i < this.handOpenTiles.length; ++i) {
            this.numOpenTiles += this.handOpenTiles[i];
        }

        if(this.numOpenTiles == 0) {
            this.isClosed = true;
        } else {
            this.isClosed = false;
        }

        if(!this.isClosed && this.rand.nextInt(3) == 1) {
            this.ronWind = 4;
        }

        if(this.ronWind == this.seatWind) {
            do {
                this.ronWind = this.rand.nextInt(4);
            } while(this.ronWind == this.seatWind);
        }

        this.addDora();
        this.countYakuman();
        this.isYakuman = this.numYakuman > 0;

        for(i = 0; i < this.handYaku.length; ++i) {
            if(i == 12) {
                ++i;
            }

            this.handYaku[i] = false;
        }

        if(!this.isYakuman) {
            this.countYaku();
            this.countHan();
            this.totalHan += this.handHan;
            this.countFu();
        }

        this.countPayments();
        this.arrayPointer = 0;
        repaint();
    }

    public String getWindString(int w) {
        switch(w) {
            case 0:
                return "東";
            case 1:
                return "南";
            case 2:
                return "西";
            case 3:
                return "北";
            default:
                return "X";
        }
    }

    public void setLegalTiles() {
        int i;
        if(this.handYaku[5]) {
            this.legalTiles[0] = this.legalTiles[8] = this.legalTiles[9] = this.legalTiles[17] = this.legalTiles[18] = this.legalTiles[26] = false;

            for(i = 27; i < 34; ++i) {
                this.legalTiles[i] = false;
            }
        }

        int j;
        if(this.handYaku[8] || this.handYaku[19] || this.handYaku[17]) {
            for(i = 0; i < 3; ++i) {
                for(j = 1; j < 8; ++j) {
                    this.legalTiles[i * 9 + j] = false;
                }
            }
        }

        if(this.handYaku[19] || this.handYaku[22]) {
            for(i = 27; i < 34; ++i) {
                this.legalTiles[i] = false;
            }
        }

        if(this.honItsu != -1) {
            for(i = 0; i < 3; ++i) {
                if(this.honItsu != i) {
                    for(j = 0; j < 9; ++j) {
                        this.legalTiles[i * 9 + j] = false;
                    }
                }
            }
        }

    }

    public int getLegalTile() {
        this.sanityCheck = 0;

        int newTile;
        do {
            newTile = this.rand.nextInt(34);
        } while(!this.sanityCheck("getLegalTile()\n") && !this.isError && !this.legalTiles[newTile]);

        return newTile;
    }

    public boolean isImpossibleSanShoku(int tile, int num) {
        return this.handTiles[tile] >= num || this.handTiles[tile + 9] >= num || this.handTiles[tile + 18] >= num;
    }

    public void addDora() {
        if(!this.isInfure && this.numKans < 3 && this.rand.nextInt(6) == 1) {
            ++this.numKans;
            if(this.numKans < 3 && this.rand.nextInt(6) == 1) {
                ++this.numKans;
                if(this.numKans < 3 && this.rand.nextInt(6) == 1) {
                    ++this.numKans;
                }
            }
        }

        if(this.numKans > 3) {
            this.numKans = 3;
        }

        int i;
        for(i = 0; i <= this.numKans; ++i) {
            do {
                this.doraTiles[i] = this.rand.nextInt(34);
            } while(this.handTiles[this.doraTiles[i]] > 3);

            ++this.handTiles[this.doraTiles[i]];
        }

        if(this.isRiichi) {
            for(i = 4; i <= 4 + this.numKans; ++i) {
                do {
                    this.doraTiles[i] = this.rand.nextInt(34);
                } while(this.handTiles[this.doraTiles[i]] > 3);

                ++this.handTiles[this.doraTiles[i]];
            }
        }

        for(i = 0; i < this.doraTiles.length; ++i) {
            if(this.doraTiles[i] > -1) {
                --this.handTiles[this.doraTiles[i]];
            }
        }

        int j;
        for(i = 0; i < 27; ++i) {
            if(this.handTiles[i] > 0) {
                if(i % 9 == 0) {
                    for(j = 0; j < this.doraTiles.length; ++j) {
                        if(this.doraTiles[j] == i + 8) {
                            this.numDora += this.handTiles[i];
                        }
                    }
                } else {
                    for(j = 0; j < this.doraTiles.length; ++j) {
                        if(this.doraTiles[j] == i - 1) {
                            this.numDora += this.handTiles[i];
                        }
                    }
                }
            }
        }

        for(i = 0; i < 3; ++i) {
            if(this.handTiles[i + 27] > 0) {
                for(j = 0; j < this.doraTiles.length; ++j) {
                    if(this.doraTiles[j] - 27 == (i + 2) % 3) {
                        this.numDora += this.handTiles[i + 27];
                    }
                }
            }
        }

        for(i = 0; i < 4; ++i) {
            if(this.handTiles[i + 30] > 0) {
                for(j = 0; j < this.doraTiles.length; ++j) {
                    if(this.doraTiles[j] - 30 == (i + 3) % 4) {
                        this.numDora += this.handTiles[i + 30];
                    }
                }
            }
        }

    }

    public void addYaku(int y) {
        //make open riichi impossible because it's a house rule
        if (y == 12) {
            //in the event open riichi was chosen, instead choose san kantsu
            this.handYaku[13] = true;
        } else {
            this.handYaku[y] = true;
        }
        label65:
        switch(y) {
            case 0:
            default:
                this.isRiichi = this.isClosed = true;
                break;
            case 1:
                this.isClosed = true;
                this.ronWind = 4;
                if(!this.isRiichi && this.rand.nextInt(2) == 1) {
                    this.addYaku(0);
                }
                break;
            case 2:
                this.compatibleYaku[5] = this.compatibleYaku[6] = this.compatibleYaku[18] = this.compatibleYaku[21] = this.compatibleYaku[19] = this.compatibleYaku[22] = false;
                break;
            case 3:
                this.compatibleYaku[5] = this.compatibleYaku[6] = this.compatibleYaku[18] = this.compatibleYaku[21] = this.compatibleYaku[22] = this.compatibleYaku[19] = false;
                break;
            case 4:
                this.compatibleYaku[5] = this.compatibleYaku[6] = this.compatibleYaku[18] = this.compatibleYaku[21] = this.compatibleYaku[22] = this.compatibleYaku[19] = this.compatibleYaku[16] = false;
                break;
            case 5:
                this.minMelds[1] = this.minMelds[7] = this.minMelds[8] = this.minMelds[9] = -1;
                this.minMelds[12] = 1;
                this.compatibleYaku[2] = this.compatibleYaku[3] = this.compatibleYaku[4] = this.compatibleYaku[8] = this.compatibleYaku[9] = this.compatibleYaku[16] = this.compatibleYaku[17] = this.compatibleYaku[19] = this.compatibleYaku[20] = false;
                break;
            case 6:
                this.isClosed = true;
                this.waitType = 2;
                this.minMelds[18] = 1;
                this.minMelds[5] = this.minMelds[6] = this.minMelds[7] = this.minMelds[8] = this.minMelds[9] = this.minMelds[10] = this.minMelds[11] = -1;
                this.compatibleYaku[2] = this.compatibleYaku[3] = this.compatibleYaku[4] = this.compatibleYaku[11] = this.compatibleYaku[13] = this.compatibleYaku[14] = this.compatibleYaku[15] = this.compatibleYaku[16] = this.compatibleYaku[17] = this.compatibleYaku[18] = false;
                if(this.rand.nextInt(2) == 1) {
                    this.addYaku(0);
                }
                break;
            case 7:
                this.isClosed = true;
                this.minMelds[2] = 1;
                this.compatibleYaku[11] = this.compatibleYaku[13] = this.compatibleYaku[14] = this.compatibleYaku[15] = this.compatibleYaku[16] = this.compatibleYaku[17] = this.compatibleYaku[18] = false;
                if(this.rand.nextInt(2) == 1) {
                    this.addYaku(0);
                }
                break;
            case 8:
                this.minMelds[0] = this.minMelds[6] = -1;
                this.minMelds[15] = this.minMelds[1] = 1;
                this.compatibleYaku[5] = this.compatibleYaku[6] = this.compatibleYaku[9] = this.compatibleYaku[14] = this.compatibleYaku[22] = this.compatibleYaku[17] = this.compatibleYaku[18] = false;
                break;
            case 9:
                this.minMelds[3] = 1;
                this.compatibleYaku[11] = this.compatibleYaku[13] = this.compatibleYaku[14] = this.compatibleYaku[6] = this.compatibleYaku[8] = this.compatibleYaku[10] = this.compatibleYaku[15] = this.compatibleYaku[16] = this.compatibleYaku[17] = this.compatibleYaku[18] = this.compatibleYaku[19] = this.compatibleYaku[21] = this.compatibleYaku[5] = false;
                break;
            case 10:
                this.minMelds[4] = 1;
                this.compatibleYaku[11] = this.compatibleYaku[13] = this.compatibleYaku[14] = this.compatibleYaku[9] = this.compatibleYaku[22] = this.compatibleYaku[15] = this.compatibleYaku[16] = this.compatibleYaku[17] = this.compatibleYaku[18] = this.compatibleYaku[20] = this.compatibleYaku[21] = false;
                break;
            case 11:
                this.minMelds[10] = 1;
                this.compatibleYaku[6] = this.compatibleYaku[7] = this.compatibleYaku[9] = this.compatibleYaku[10] = this.compatibleYaku[16] = this.compatibleYaku[18] = this.compatibleYaku[20] = this.compatibleYaku[21] = this.compatibleYaku[22] = false;
                break;
            /*house rule
            case 12:
                this.isRiichi = this.isClosed = true;
                if(this.rand.nextInt(4) >= 1) {
                    if(this.compatibleYaku[1]) {
                        this.addYaku(1);
                    }
                    break;
                } else {
                    while(true) {
                        this.ronWind = this.rand.nextInt(4);
                        if(this.ronWind != this.seatWind) {
                            break label65;
                        }
                    }
                }*/
            case 12: //if open riichi is ever chosen, instead choose san kantsu
            case 13:
                this.minMelds[11] = 3;
                this.compatibleYaku[6] = this.compatibleYaku[7] = this.compatibleYaku[9] = this.compatibleYaku[10] = this.compatibleYaku[21] = this.compatibleYaku[18] = this.compatibleYaku[8] = this.compatibleYaku[19] = false;
                break;
            case 14:
                this.minMelds[0] = this.minMelds[1] = this.minMelds[2] = this.minMelds[3] = this.minMelds[4] = -1;
                this.compatibleYaku[6] = this.compatibleYaku[7] = this.compatibleYaku[9] = this.compatibleYaku[10] = this.compatibleYaku[12] = this.compatibleYaku[1] = this.compatibleYaku[21] = this.compatibleYaku[18] = this.compatibleYaku[0] = this.compatibleYaku[19] = this.compatibleYaku[8] = false;
                break;
            case 15:
                this.minMelds[5] = 3;
                this.compatibleYaku[6] = this.compatibleYaku[7] = this.compatibleYaku[9] = this.compatibleYaku[10] = this.compatibleYaku[21] = this.compatibleYaku[18] = false;
                break;
            case 16:
                this.minMelds[16] = 1;
                this.compatibleYaku[5] = this.compatibleYaku[6] = this.compatibleYaku[18] = this.compatibleYaku[9] = this.compatibleYaku[10] = this.compatibleYaku[11] = this.compatibleYaku[21] = this.compatibleYaku[19] = this.compatibleYaku[22] = this.compatibleYaku[4] = false;
                break;
            case 17:
                this.minMelds[0] = this.minMelds[6] = this.minMelds[1] = this.minMelds[2] = this.minMelds[3] = this.minMelds[4] = -1;
                this.minMelds[15] = 1;
                this.minMelds[9] = 4;
                this.compatibleYaku[5] = this.compatibleYaku[6] = this.compatibleYaku[9] = this.compatibleYaku[10] = this.compatibleYaku[0] = this.compatibleYaku[1] = this.compatibleYaku[7] = this.compatibleYaku[12] = this.compatibleYaku[22] = this.compatibleYaku[19] = this.compatibleYaku[8] = false;
                break;
            case 18:
                this.waitType = 4;
                this.compatibleYaku[2] = this.compatibleYaku[3] = this.compatibleYaku[4] = this.compatibleYaku[6] = this.compatibleYaku[7] = this.compatibleYaku[10] = this.compatibleYaku[9] = this.compatibleYaku[11] = this.compatibleYaku[13] = this.compatibleYaku[14] = this.compatibleYaku[15] = this.compatibleYaku[16] = this.compatibleYaku[21] = this.compatibleYaku[8] = this.compatibleYaku[19] = false;
                break;
            case 19:
                this.minMelds[0] = this.minMelds[6] = this.minMelds[8] = this.minMelds[9] = -1;
                this.minMelds[13] = this.minMelds[1] = 1;
                this.compatibleYaku[5] = this.compatibleYaku[16] = this.compatibleYaku[9] = this.compatibleYaku[18] = this.compatibleYaku[22] = this.compatibleYaku[20] = this.compatibleYaku[17] = this.compatibleYaku[14] = false;
                break;
            case 20:
                this.minMelds[4] = this.minMelds[10] = -1;
                this.compatibleYaku[5] = this.compatibleYaku[10] = this.compatibleYaku[11] = this.compatibleYaku[19] = this.compatibleYaku[22] = false;
                break;
            case 21:
                this.isClosed = true;
                this.minMelds[2] = 2;
                this.compatibleYaku[2] = this.compatibleYaku[3] = this.compatibleYaku[4] = this.compatibleYaku[13] = this.compatibleYaku[14] = this.compatibleYaku[15] = this.compatibleYaku[16] = this.compatibleYaku[17] = this.compatibleYaku[18] = this.compatibleYaku[9] = this.compatibleYaku[10] = this.compatibleYaku[11] = false;
                if(this.rand.nextInt(2) == 1) {
                    this.addYaku(0);
                }
                break;
            case 22:
                this.minMelds[4] = this.minMelds[10] = -1;
                this.compatibleYaku[16] = this.compatibleYaku[10] = this.compatibleYaku[11] = this.compatibleYaku[2] = this.compatibleYaku[3] = this.compatibleYaku[4] = this.compatibleYaku[17] = this.compatibleYaku[19] = this.compatibleYaku[8] = false;
        }

        if(this.isInfure && this.rand.nextInt(2) == 1) {
            for(int i = 0; i < 5; ++i) {
                this.arrayPointer = this.rand.nextInt(23);
                if(this.compatibleYaku[this.arrayPointer]) {
                    this.addYaku(this.arrayPointer);
                    i = 5;
                }
            }
        }

    }

    public void addYakuman(int y) {
        boolean isYakumanSpecial = false;
        int i;
        switch(y) {
            case 0:
            default:
                this.addMeld(4, 27, 2);
                this.addMeld(4, 29, 2);
                this.addMeld(4, 28, 2);
                this.curMeld = 1;
                this.numMelds = 3;
                break;
            case 2:
                isYakumanSpecial = true;
            case 1:
                this.isClosed = true;
                this.forceAnKous = 4;
                this.addYaku(14);
                if(isYakumanSpecial) {
                    this.waitType = 4;
                } else {
                    this.waitType = 3;
                    this.addYaku(1);
                }
                break;
            case 3:
                this.minMelds[8] = 4;
                this.minMelds[14] = 1;
                this.addYaku(14);
                break;
            case 4:
                if(this.rand.nextInt(2) == 1) {
                    this.addMeld(3, 28, 0);
                    this.isJantouSet = true;
                } else {
                    this.addMeld(4, 28, 2);
                    ++this.numMelds;
                    --this.curMeld;
                }

                while(this.numMelds < 4) {
                    this.arrayPointer = this.rand.nextInt(6);
                    switch(this.arrayPointer) {
                        case 0:
                        default:
                            this.isMeldPossible = true;
                            this.isMeldPossible = this.handTiles[19] < 4 && this.handTiles[20] < 4 && this.handTiles[21] < 4;
                            if(this.isMeldPossible) {
                                this.addMeld(0, 19, 2);
                                ++this.numMelds;
                                --this.curMeld;
                            }
                            break;
                        case 1:
                            if(this.handTiles[19] <= 1) {
                                this.addMeld(4, 19, 2);
                                ++this.numMelds;
                                --this.curMeld;
                            }
                            break;
                        case 2:
                            if(this.handTiles[20] <= 1) {
                                this.addMeld(4, 20, 2);
                                ++this.numMelds;
                                --this.curMeld;
                            }
                            break;
                        case 3:
                            if(this.handTiles[21] <= 1) {
                                this.addMeld(4, 21, 2);
                                ++this.numMelds;
                                --this.curMeld;
                            }
                            break;
                        case 4:
                            if(this.handTiles[23] <= 1) {
                                this.addMeld(4, 23, 2);
                                ++this.numMelds;
                                --this.curMeld;
                            }
                            break;
                        case 5:
                            if(this.handTiles[25] <= 1) {
                                this.addMeld(4, 25, 2);
                                ++this.numMelds;
                                --this.curMeld;
                            }
                    }

                    if(this.sanityCheck("case 4:\t\t//Ryuu 1 sou\n") || this.isError) {
                        break;
                    }
                }

                if(!this.isJantouSet) {
                    for(i = 0; i < 12; ++i) {
                        this.arrayPointer = this.rand.nextInt(5) + 19;
                        if(this.arrayPointer == 22) {
                            this.arrayPointer = 25;
                        }

                        if(!this.isJantouSet && this.handTiles[this.arrayPointer] <= 2) {
                            this.addMeld(3, this.arrayPointer, 0);
                            this.isJantouSet = true;
                        }
                    }
                }
                break;
            /*house rule. never create this
            case 5:
                for(i = 0; i < 7; ++i) {
                    this.addMeld(3, 10 + i, 0);
                }

                this.curMeld = 0;
                this.numMelds = 7;
                this.isJantouSet = true;
                break;*/
            //if chariot was chosen, instead choose this one because we never want to create chariot since it's a house rule
            case 5:
            case 6:
                this.minMelds[7] = 4;
                this.minMelds[13] = 1;
                this.addYaku(14);
                break;
            case 8:
                isYakumanSpecial = true;
            case 7:
                this.handTiles[0] = 1;
                this.handTiles[8] = 1;
                this.handTiles[9] = 1;
                this.handTiles[17] = 1;
                this.handTiles[18] = 1;
                this.handTiles[26] = 1;
                this.handTiles[27] = 1;
                this.handTiles[28] = 1;
                this.handTiles[29] = 1;
                this.handTiles[30] = 1;
                this.handTiles[31] = 1;
                this.handTiles[32] = 1;
                this.handTiles[33] = 1;
                this.handClosedTiles[0] = 1;
                this.handClosedTiles[8] = 1;
                this.handClosedTiles[9] = 1;
                this.handClosedTiles[17] = 1;
                this.handClosedTiles[18] = 1;
                this.handClosedTiles[26] = 1;
                this.handClosedTiles[27] = 1;
                this.handClosedTiles[28] = 1;
                this.handClosedTiles[29] = 1;
                this.handClosedTiles[30] = 1;
                this.handClosedTiles[31] = 1;
                this.handClosedTiles[32] = 1;
                this.handClosedTiles[33] = 1;
                this.arrayPointer = this.rand.nextInt(2);
                if(this.arrayPointer == 0) {
                    this.arrayPointer = this.rand.nextInt(7) + 27;
                    ++this.handTiles[this.arrayPointer];
                } else {
                    this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                    ++this.handTiles[this.arrayPointer];
                }

                if(isYakumanSpecial) {
                    this.agariHai = this.arrayPointer;
                } else {
                    ++this.handClosedTiles[this.arrayPointer];
                }

                this.curMeld = 0;
                this.numMelds = 13;
                this.isJantouSet = true;
                break;
            case 9:
                for(i = 0; i < 3; ++i) {
                    this.arrayPointer = this.rand.nextInt(4) + 30;

                    for(int j = 0; j < 12; ++j) {
                        if(this.handTiles[this.arrayPointer] > 1) {
                            this.arrayPointer = this.rand.nextInt(4) + 30;
                        }
                    }

                    if(this.handTiles[this.arrayPointer] <= 1) {
                        this.addMeld(4, this.arrayPointer, 2);
                        ++this.numMelds;
                        --this.curMeld;
                    }
                }

                this.minMelds[17] = 1;
                break;
            case 10:
                this.addMeld(4, 30, 2);
                this.addMeld(4, 31, 2);
                this.addMeld(4, 32, 2);
                this.addMeld(4, 33, 2);
                this.curMeld = 0;
                this.numMelds = 4;
                break;
            case 11:
                this.forceKans = 4;
                this.addYaku(14);
                break;
            case 13:
                isYakumanSpecial = true;
            case 12:
            case 14: //since we made 14 impossible, we'll use chuuren poutou if 14 ever gets picked
                this.honItsu = this.rand.nextInt(3);
                this.handTiles[0 + 9 * this.honItsu] = 3;
                this.handTiles[1 + 9 * this.honItsu] = 1;
                this.handTiles[2 + 9 * this.honItsu] = 1;
                this.handTiles[3 + 9 * this.honItsu] = 1;
                this.handTiles[4 + 9 * this.honItsu] = 1;
                this.handTiles[5 + 9 * this.honItsu] = 1;
                this.handTiles[6 + 9 * this.honItsu] = 1;
                this.handTiles[7 + 9 * this.honItsu] = 1;
                this.handTiles[8 + 9 * this.honItsu] = 3;
                this.handClosedTiles[0 + 9 * this.honItsu] = 3;
                this.handClosedTiles[1 + 9 * this.honItsu] = 1;
                this.handClosedTiles[2 + 9 * this.honItsu] = 1;
                this.handClosedTiles[3 + 9 * this.honItsu] = 1;
                this.handClosedTiles[4 + 9 * this.honItsu] = 1;
                this.handClosedTiles[5 + 9 * this.honItsu] = 1;
                this.handClosedTiles[6 + 9 * this.honItsu] = 1;
                this.handClosedTiles[7 + 9 * this.honItsu] = 1;
                this.handClosedTiles[8 + 9 * this.honItsu] = 3;
                this.arrayPointer = this.rand.nextInt(9) + 9 * this.honItsu;
                ++this.handTiles[this.arrayPointer];
                if(isYakumanSpecial) {
                    this.agariHai = this.arrayPointer;
                } else {
                    ++this.handClosedTiles[this.arrayPointer];
                }

                this.curMeld = 0;
                this.numMelds = 9;
                this.isJantouSet = true;
                break;
            /*house rule, make this impossible
            case 14:
                this.compatibleYaku[1] = false;
                this.addYaku(12);
                break;
             */
            case 1337:
                this.addMeld(1, 0, 0);
                this.addMeld(1, 1, 0);
                this.addMeld(1, 2, 0);
                this.addMeld(0, 18, 2);
                this.addMeld(3, 28, 0);
                this.curMeld = 0;
                this.numMelds = 4;
                this.isJantouSet = true;
        }

    }

    public void addHandTiles() {
        int numKouMelds = 0;

        int i;
        for(i = 5; i < 12; ++i) {
            if(this.minMelds[i] > 0) {
                numKouMelds += this.minMelds[i];
                if(i == 10) {
                    numKouMelds += 2 * this.minMelds[i];
                }
            }
        }

        if(this.handYaku[2]) {
            ++numKouMelds;
        }

        if(this.handYaku[3]) {
            ++numKouMelds;
        }

        if(this.handYaku[4]) {
            ++numKouMelds;
        }

        if(this.handYaku[16]) {
            numKouMelds += 2;
        }

        if(numKouMelds > 3) {
            ++this.forceMinKous;
        }

        this.sanityCheck = 0;

        while(numKouMelds > 4) {
            if(this.minMelds[5] >= this.minMelds[11]) {
                if(this.minMelds[5] > 0) {
                    ++this.forceAnKous;
                    --this.minMelds[5];
                }
            } else if(this.minMelds[11] > this.minMelds[5]) {
                ++this.forceKans;
                --this.minMelds[11];
            }

            --numKouMelds;
            if(this.sanityCheck("while ( numKouMelds > 4 ) {\n") || this.isError) {
                break;
            }
        }

        if(this.handYaku[2]) {
            this.addMeld(4, 30 + this.roundWind, 2);
            --this.curMeld;
            ++this.numMelds;
        }

        if(this.handYaku[3] && this.handTiles[30 + this.seatWind] < 2) {
            this.addMeld(4, 30 + this.seatWind, 2);
            --this.curMeld;
            ++this.numMelds;
        }

        if(this.handYaku[4]) {
            this.arrayPointer = this.rand.nextInt(3) + 27;
            if(this.handTiles[this.arrayPointer] < 2) {
                this.addMeld(4, this.arrayPointer, 2);
                --this.curMeld;
                ++this.numMelds;
            }
        }

        if(this.handYaku[16]) {
            do {
                this.arrayPointer = this.rand.nextInt(3) + 27;
            } while(!this.sanityCheck("if (handYaku[16]) {\n") && !this.isError && this.handTiles[this.arrayPointer] > 1);

            if(this.numMelds < 4) {
                this.addMeld(4, this.arrayPointer, 2);
                --this.curMeld;
                ++this.numMelds;
            }

            this.arrayPointer = this.rand.nextInt(3) + 27;

            for(i = 0; i < 10; ++i) {
                if(this.handTiles[this.arrayPointer] > 1) {
                    this.arrayPointer = this.rand.nextInt(3) + 27;
                }
            }

            if(this.handTiles[this.arrayPointer] < 2 && this.numMelds < 4) {
                this.addMeld(4, this.arrayPointer, 2);
                --this.curMeld;
                ++this.numMelds;
            }
        }

        if(this.handYaku[20] || this.handYaku[22]) {
            this.honItsu = this.rand.nextInt(3);
        }

        this.setLegalTiles();
        if(this.handYaku[18]) {
            for(i = 0; i < 7; ++i) {
                do {
                    this.arrayPointer = this.getLegalTile();
                } while(!this.sanityCheck("if (handYaku[18]) {\n") && !this.isError && this.handTiles[this.arrayPointer] > 1);

                this.addMeld(3, this.arrayPointer, 0);
            }

            this.curMeld = 0;
            this.numMelds = 7;
            this.isJantouSet = true;
        }

        while(this.curMeld > 0) {
            for(i = 0; i < 12; ++i) {
                if(this.minMelds[i] >= this.curMeld && this.numMelds < 4) {
                    --this.minMelds[i];
                    this.verifyAndAddMeld(i);
                }
            }

            --this.curMeld;
            if(this.sanityCheck("while ( curMeld > 0 ) {\n") || this.isError) {
                break;
            }
        }

        this.minMelds[4] = this.minMelds[3] = this.minMelds[5] = -1;

        while(this.numMelds < 4) {
            this.arrayPointer = this.rand.nextInt(9);
            if(this.minMelds[this.arrayPointer] >= 0) {
                this.verifyAndAddMeld(this.arrayPointer);
            }

            if(this.sanityCheck("while ( numMelds < 4 ) {\n") || this.isError) {
                break;
            }
        }

        for(i = 6; i >= 0; --i) {
            if(this.minMelds[12 + i] > 0 && !this.isJantouSet) {
                this.verifyAndAddMeld(12 + i);
            }
        }

        if(!this.isJantouSet) {
            this.sanityCheck = 0;

            do {
                this.arrayPointer = this.getLegalTile();
            } while(!this.sanityCheck("if (!isJantouSet) {\n") && !this.isError && this.handTiles[this.arrayPointer] >= 3);

            this.addMeld(3, this.arrayPointer, 0);
        }

    }

    public void verifyAndAddMeld(int type) {
        this.sanityCheck = 0;
        int temp;
        int temp4;
        int k;
        int k1;
        switch(type) {
            case 0:
            default:
                this.isMeldPossible = true;
                if(this.honItsu > -1) {
                    this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.honItsu;
                } else {
                    this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.rand.nextInt(3);
                }

                for(temp = 0; temp < 9; ++temp) {
                    this.isMeldPossible = this.handTiles[this.arrayPointer] < 4 && this.handTiles[this.arrayPointer + 1] < 4 && this.handTiles[this.arrayPointer + 2] < 4;
                    if(!this.isMeldPossible) {
                        if(this.honItsu > -1) {
                            this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.honItsu;
                        } else {
                            this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.rand.nextInt(3);
                        }
                    }
                }

                if(this.isMeldPossible) {
                    this.addMeld(0, this.arrayPointer, 2);
                    ++this.numMelds;
                }
                break;
            case 1:
                this.isMeldPossible = true;
                if(this.honItsu > -1) {
                    this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.honItsu;
                } else {
                    this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                }

                for(temp = 0; temp < 9; ++temp) {
                    this.isMeldPossible = this.handTiles[this.arrayPointer] < 4 && this.handTiles[this.arrayPointer + 1] < 4 && this.handTiles[this.arrayPointer + 2] < 4;
                    if(!this.isMeldPossible) {
                        if(this.honItsu > -1) {
                            this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.honItsu;
                        } else {
                            this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                        }
                    }
                }

                if(this.isMeldPossible) {
                    this.addMeld(0, this.arrayPointer, 2);
                    ++this.numMelds;
                }
                break;
            case 2:
                if(this.numMelds < 3) {
                    this.isMeldPossible = true;
                    if(!this.handYaku[8] && !this.handYaku[19]) {
                        if(this.handYaku[5]) {
                            if(this.honItsu > -1) {
                                this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.honItsu;
                            } else {
                                this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.rand.nextInt(3);
                            }

                            for(temp = 0; temp < 9; ++temp) {
                                this.isMeldPossible = this.handTiles[this.arrayPointer] <= 2 && this.handTiles[this.arrayPointer + 1] <= 2 && this.handTiles[this.arrayPointer + 2] <= 2;
                                if(!this.isMeldPossible) {
                                    if(this.honItsu > -1) {
                                        this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.honItsu;
                                    } else {
                                        this.arrayPointer = 1 + this.rand.nextInt(5) + 9 * this.rand.nextInt(3);
                                    }
                                }
                            }
                        } else {
                            if(this.honItsu > -1) {
                                this.arrayPointer = this.rand.nextInt(7) + 9 * this.honItsu;
                            } else {
                                this.arrayPointer = this.rand.nextInt(7) + 9 * this.rand.nextInt(3);
                            }

                            for(temp = 0; temp < 9; ++temp) {
                                this.isMeldPossible = this.handTiles[this.arrayPointer] <= 2 && this.handTiles[this.arrayPointer + 1] <= 2 && this.handTiles[this.arrayPointer + 2] <= 2;
                                if(!this.isMeldPossible) {
                                    if(this.honItsu > -1) {
                                        this.arrayPointer = this.rand.nextInt(7) + 9 * this.honItsu;
                                    } else {
                                        this.arrayPointer = this.rand.nextInt(7) + 9 * this.rand.nextInt(3);
                                    }
                                }
                            }
                        }
                    } else {
                        if(this.honItsu > -1) {
                            this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.honItsu;
                        } else {
                            this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                        }

                        for(temp = 0; temp < 9; ++temp) {
                            this.isMeldPossible = this.handTiles[this.arrayPointer] <= 2 && this.handTiles[this.arrayPointer + 1] <= 2 && this.handTiles[this.arrayPointer + 2] <= 2;
                            if(!this.isMeldPossible) {
                                if(this.honItsu > -1) {
                                    this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.honItsu;
                                } else {
                                    this.arrayPointer = 6 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                                }
                            }
                        }
                    }

                    if(this.isMeldPossible) {
                        this.addMeld(0, this.arrayPointer, 2);
                        this.addMeld(0, this.arrayPointer, 2);
                        this.numMelds += 2;
                    }
                }
                break;
            case 3:
                if(this.numMelds < 2) {
                    this.isMeldPossible = true;
                    if(this.honItsu > -1) {
                        for(temp = 0; temp < 9; ++temp) {
                            this.isMeldPossible = this.isMeldPossible && this.handTiles[temp + 9 * this.honItsu] < 4;
                        }
                    } else {
                        this.arrayPointer = this.rand.nextInt(3);

                        for(temp = 0; temp < 3; ++temp) {
                            for(temp4 = 0; temp4 < 9; ++temp4) {
                                this.isMeldPossible = this.isMeldPossible && this.handTiles[temp4 + 9 * this.arrayPointer] < 4;
                            }

                            if(!this.isMeldPossible) {
                                this.arrayPointer = this.rand.nextInt(3);
                            }
                        }
                    }

                    if(this.isMeldPossible) {
                        if(this.honItsu > -1) {
                            this.arrayPointer = this.honItsu;
                        }

                        this.addMeld(0, 0 + 9 * this.arrayPointer, 2);
                        this.addMeld(0, 3 + 9 * this.arrayPointer, 2);
                        this.addMeld(0, 6 + 9 * this.arrayPointer, 2);
                        this.numMelds += 3;
                    }
                }
                break;
            case 4:
                if(this.numMelds < 2) {
                    this.isMeldPossible = true;
                    if(!this.handYaku[8] && !this.handYaku[19]) {
                        if(this.handYaku[5]) {
                            this.arrayPointer = 1 + this.rand.nextInt(5);

                            for(temp = 0; temp < 9; ++temp) {
                                this.isMeldPossible = !this.isImpossibleSanShoku(this.arrayPointer, 4) && !this.isImpossibleSanShoku(this.arrayPointer + 1, 4) && !this.isImpossibleSanShoku(this.arrayPointer + 2, 4);
                                if(!this.isMeldPossible) {
                                    this.arrayPointer = 1 + this.rand.nextInt(5);
                                }
                            }
                        } else {
                            this.arrayPointer = this.rand.nextInt(7);

                            for(temp = 0; temp < 9; ++temp) {
                                this.isMeldPossible = !this.isImpossibleSanShoku(this.arrayPointer, 4) && !this.isImpossibleSanShoku(this.arrayPointer + 1, 4) && !this.isImpossibleSanShoku(this.arrayPointer + 2, 4);
                                if(!this.isMeldPossible) {
                                    this.arrayPointer = this.rand.nextInt(7);
                                }
                            }
                        }
                    } else {
                        this.arrayPointer = 6 * this.rand.nextInt(2);

                        for(temp = 0; temp < 9; ++temp) {
                            this.isMeldPossible = !this.isImpossibleSanShoku(this.arrayPointer, 4) && !this.isImpossibleSanShoku(this.arrayPointer + 1, 4) && !this.isImpossibleSanShoku(this.arrayPointer + 2, 4);
                            if(!this.isMeldPossible) {
                                this.arrayPointer = 6 * this.rand.nextInt(2);
                            }
                        }
                    }

                    if(this.isMeldPossible) {
                        this.addMeld(0, this.arrayPointer, 2);
                        this.addMeld(0, this.arrayPointer + 9, 2);
                        this.addMeld(0, this.arrayPointer + 18, 2);
                        this.numMelds += 3;
                    }
                }
                break;
            case 5:
                this.arrayPointer = this.getLegalTile();

                for(temp = 0; temp < 10; ++temp) {
                    if(this.handTiles[this.arrayPointer] >= 2) {
                        this.arrayPointer = this.getLegalTile();
                    }
                }

                if(this.handTiles[this.arrayPointer] < 2) {
                    this.addMeld(4, this.arrayPointer, 0);
                    ++this.numMelds;
                }
                break;
            case 6:
                if(this.honItsu > -1) {
                    do {
                        this.arrayPointer = 1 + this.rand.nextInt(7) + 9 * this.honItsu;
                    } while(!this.sanityCheck("case 6: // Kou - Tan yao\nif (honItsu > -1) {\n") && !this.isError && this.handTiles[this.arrayPointer] >= 2);
                } else {
                    do {
                        this.arrayPointer = 1 + this.rand.nextInt(7) + 9 * this.rand.nextInt(3);
                    } while(!this.sanityCheck("case 6: // Kou - Tan yao\nelse\n") && !this.isError && this.handTiles[this.arrayPointer] >= 2);
                }

                this.addMeld(4, this.arrayPointer, 2);
                ++this.numMelds;
                break;
            case 7:
                if(this.honItsu > -1) {
                    temp = this.rand.nextInt(2);
                    this.arrayPointer = temp * 8 + 9 * this.honItsu;
                    if(this.handTiles[this.arrayPointer] < 2) {
                        this.addMeld(4, this.arrayPointer, 2);
                        ++this.numMelds;
                    } else {
                        this.arrayPointer = (1 - temp) * 8 + 9 * this.honItsu;
                    }

                    if(this.handTiles[this.arrayPointer] < 2) {
                        this.addMeld(4, this.arrayPointer, 2);
                        ++this.numMelds;
                    }
                } else {
                    this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);

                    for(temp = 0; temp < 9; ++temp) {
                        if(this.handTiles[this.arrayPointer] >= 2) {
                            this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                        }
                    }

                    if(this.handTiles[this.arrayPointer] < 2) {
                        this.addMeld(4, this.arrayPointer, 2);
                        ++this.numMelds;
                    }
                }
                break;
            case 8:
                this.arrayPointer = this.rand.nextInt(7) + 27;

                for(temp = 0; temp < 9; ++temp) {
                    if(this.handTiles[this.arrayPointer] >= 2) {
                        this.arrayPointer = this.rand.nextInt(7) + 27;
                    }
                }

                if(this.handTiles[this.arrayPointer] < 2) {
                    this.addMeld(4, this.arrayPointer, 2);
                    ++this.numMelds;
                }
                break;
            case 9:
                if(this.honItsu > -1) {
                    temp = this.rand.nextInt(9);
                    if(temp != 0 && temp != 1) {
                        this.arrayPointer = temp + 25;
                    } else {
                        this.arrayPointer = temp * 8 + 9 * this.honItsu;
                    }

                    for(temp4 = 0; temp4 < 9; ++temp4) {
                        if(this.handTiles[this.arrayPointer] >= 2) {
                            temp = this.rand.nextInt(9);
                            if(temp != 0 && temp != 1) {
                                this.arrayPointer = temp + 25;
                            } else {
                                this.arrayPointer = temp * 8 + 9 * this.honItsu;
                            }
                        }
                    }

                    if(this.handTiles[this.arrayPointer] < 2) {
                        this.addMeld(4, this.arrayPointer, 2);
                        ++this.numMelds;
                    }
                } else {
                    temp = this.rand.nextInt(2);

                    for(temp4 = 0; temp4 < 9; ++temp4) {
                        if(temp == 0) {
                            this.arrayPointer = this.rand.nextInt(7) + 27;

                            for(k = 0; k < 9; ++k) {
                                if(this.handTiles[this.arrayPointer] >= 2) {
                                    this.arrayPointer = this.rand.nextInt(7) + 27;
                                }
                            }
                        } else {
                            this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);

                            for(k = 0; k < 9; ++k) {
                                if(this.handTiles[this.arrayPointer] >= 2) {
                                    this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                                }
                            }
                        }
                    }

                    if(this.handTiles[this.arrayPointer] < 2) {
                        this.addMeld(4, this.arrayPointer, 2);
                        ++this.numMelds;
                    }
                }
                break;
            case 10:
                if(this.numMelds < 2) {
                    temp4 = this.rand.nextInt(2) + 1;
                    this.arrayPointer = this.rand.nextInt(9);

                    for(k = 0; k < 9; ++k) {
                        if(!this.legalTiles[this.arrayPointer]) {
                            this.arrayPointer = (this.arrayPointer + temp4) % 9;
                        }
                    }

                    for(k = 0; k < 9; ++k) {
                        if(this.isImpossibleSanShoku(this.arrayPointer, 2)) {
                            this.arrayPointer = this.rand.nextInt(9);

                            for(k1 = 0; k1 < 9; ++k1) {
                                if(!this.legalTiles[this.arrayPointer]) {
                                    this.arrayPointer = (this.arrayPointer + temp4) % 9;
                                }
                            }
                        }
                    }

                    if(!this.isImpossibleSanShoku(this.arrayPointer, 2) && this.legalTiles[this.arrayPointer]) {
                        this.addMeld(4, this.arrayPointer, 2);
                        this.addMeld(4, this.arrayPointer + 9, 2);
                        this.addMeld(4, this.arrayPointer + 18, 2);
                        this.numMelds += 3;
                    }
                }
                break;
            case 11:
                do {
                    this.arrayPointer = this.getLegalTile();
                } while(!this.sanityCheck("case 11:  //Kan\n") && !this.isError && this.handTiles[this.arrayPointer] >= 1);

                this.addMeld(2, this.arrayPointer, 2);
                ++this.numMelds;
                break;
            case 12:
                if(this.honItsu > -1) {
                    do {
                        this.arrayPointer = 1 + this.rand.nextInt(7) + 9 * this.honItsu;
                    } while(!this.sanityCheck("case 12: //Atama - Tan yao\nif (honItsu > -1) {\n") && !this.isError && this.handTiles[this.arrayPointer] >= 3);
                } else {
                    do {
                        this.arrayPointer = 1 + this.rand.nextInt(7) + 9 * this.rand.nextInt(3);
                    } while(!this.sanityCheck("case 12: //Atama - Tan yao\nelse {\n") && !this.isError && this.handTiles[this.arrayPointer] >= 3);
                }

                this.addMeld(3, this.arrayPointer, 0);
                this.isJantouSet = true;
                break;
            case 13:
                if(this.honItsu > -1) {
                    temp4 = this.rand.nextInt(2);
                    this.arrayPointer = temp4 * 8 + 9 * this.honItsu;
                    if(this.handTiles[this.arrayPointer] < 3) {
                        this.addMeld(3, this.arrayPointer, 0);
                        this.isJantouSet = true;
                    } else {
                        this.arrayPointer = (1 - temp4) * 8 + 9 * this.honItsu;
                    }

                    if(this.handTiles[this.arrayPointer] < 3) {
                        this.addMeld(3, this.arrayPointer, 0);
                        this.isJantouSet = true;
                    }
                } else {
                    this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);

                    for(temp4 = 0; temp4 < 9; ++temp4) {
                        if(this.handTiles[this.arrayPointer] >= 3) {
                            this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                        }
                    }

                    if(this.handTiles[this.arrayPointer] < 3) {
                        this.addMeld(3, this.arrayPointer, 0);
                        this.isJantouSet = true;
                    }
                }
                break;
            case 14:
                this.arrayPointer = this.rand.nextInt(7) + 27;

                for(temp4 = 0; temp4 < 9; ++temp4) {
                    if(this.handTiles[this.arrayPointer] >= 3) {
                        this.arrayPointer = this.rand.nextInt(7) + 27;
                    }
                }

                if(this.handTiles[this.arrayPointer] < 3) {
                    this.addMeld(3, this.arrayPointer, 0);
                    this.isJantouSet = true;
                }
                break;
            case 15:
                if(this.honItsu > -1) {
                    temp4 = this.rand.nextInt(9);
                    if(temp4 != 0 && temp4 != 1) {
                        this.arrayPointer = temp4 + 25;
                    } else {
                        this.arrayPointer = temp4 * 8 + 9 * this.honItsu;
                    }

                    for(k = 0; k < 9; ++k) {
                        if(this.handTiles[this.arrayPointer] >= 3) {
                            temp4 = this.rand.nextInt(9);
                            if(temp4 != 0 && temp4 != 1) {
                                this.arrayPointer = temp4 + 25;
                            } else {
                                this.arrayPointer = temp4 * 8 + 9 * this.honItsu;
                            }
                        }
                    }

                    if(this.handTiles[this.arrayPointer] < 3) {
                        this.addMeld(3, this.arrayPointer, 0);
                        this.isJantouSet = true;
                    }
                } else {
                    temp4 = this.rand.nextInt(2);

                    for(k = 0; k < 9; ++k) {
                        if(temp4 == 0) {
                            this.arrayPointer = this.rand.nextInt(7) + 27;

                            for(k1 = 0; k1 < 9; ++k1) {
                                if(this.handTiles[this.arrayPointer] >= 3) {
                                    this.arrayPointer = this.rand.nextInt(7) + 27;
                                }
                            }
                        } else {
                            this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);

                            for(k1 = 0; k1 < 9; ++k1) {
                                if(this.handTiles[this.arrayPointer] >= 3) {
                                    this.arrayPointer = 8 * this.rand.nextInt(2) + 9 * this.rand.nextInt(3);
                                }
                            }
                        }
                    }

                    if(this.handTiles[this.arrayPointer] < 3) {
                        this.addMeld(3, this.arrayPointer, 0);
                        this.isJantouSet = true;
                    }
                }
                break;
            case 16:
                this.arrayPointer = this.rand.nextInt(3) + 27;

                for(k = 0; k < 12; ++k) {
                    if(this.handTiles[this.arrayPointer] > 2) {
                        this.arrayPointer = this.rand.nextInt(3) + 27;
                    }
                }

                if(this.handTiles[this.arrayPointer] <= 2) {
                    this.addMeld(3, this.arrayPointer, 0);
                    this.isJantouSet = true;
                }
                break;
            case 17:
                this.arrayPointer = this.rand.nextInt(4) + 30;

                for(k = 0; k < 12; ++k) {
                    if(this.handTiles[this.arrayPointer] > 2) {
                        this.arrayPointer = this.rand.nextInt(4) + 30;
                    }
                }

                if(this.handTiles[this.arrayPointer] <= 2) {
                    this.addMeld(3, this.arrayPointer, 0);
                    this.isJantouSet = true;
                }
                break;
            case 18:
                this.arrayPointer = this.getLegalTile();

                for(k = 0; k < 20; ++k) {
                    if(this.handTiles[this.arrayPointer] >= 3) {
                        this.arrayPointer = this.getLegalTile();
                    }

                    if(this.arrayPointer >= 27) {
                        if(this.arrayPointer >= 30 + this.roundWind) {
                            this.arrayPointer = this.getLegalTile();
                        }

                        if(this.arrayPointer >= 30 + this.seatWind) {
                            this.arrayPointer = this.getLegalTile();
                        }

                        if(this.arrayPointer < 30) {
                            this.arrayPointer = this.getLegalTile();
                        }
                    }
                }

                if(this.handTiles[this.arrayPointer] < 3) {
                    this.addMeld(3, this.arrayPointer, 0);
                    this.isJantouSet = true;
                }
        }

    }

    public void addMeld(int type, int tile, int close) {
        if(close == 2) {
            close = this.rand.nextInt(2);
        }

        if(this.isClosed) {
            close = 0;
        }

        if(type == 4) {
            type = 1;
            if(this.rand.nextInt(4) == 1 && this.numKans < 3 && this.handTiles[tile] == 0) {
                type = 2;
            }
        }

        if(type == 2 && this.forceAnKous > 0 && close == 1) {
            close = 0;
            --this.forceAnKous;
        }

        if(type == 1) {
            if(this.forceKans > 0) {
                type = 2;
                --this.forceKans;
            }

            if(this.forceAnKous > 0 && close == 1) {
                close = 0;
                --this.forceAnKous;
            } else if(this.forceMinKous > 0 && close == 0) {
                close = 1;
                --this.forceMinKous;
                if(this.isClosed) {
                    close = 0;
                }
            }
        }

        int o;
        switch(type) {
            case 0:
                ++this.handTiles[tile];
                ++this.handTiles[tile + 1];
                ++this.handTiles[tile + 2];
                if(close == 1) {
                    ++this.handOpenTiles[tile];
                    this.handOpenDisp[this.curOpenTile] = tile;
                    ++this.curOpenTile;
                    ++this.handOpenTiles[tile + 1];
                    this.handOpenDisp[this.curOpenTile] = tile + 1;
                    ++this.curOpenTile;
                    ++this.handOpenTiles[tile + 2];
                    this.handOpenDisp[this.curOpenTile] = tile + 2;
                    ++this.curOpenTile;
                } else {
                    ++this.handClosedTiles[tile];
                    ++this.handClosedTiles[tile + 1];
                    ++this.handClosedTiles[tile + 2];
                }

                this.meldTile[this.meldPointer] = tile;
                this.meldType[this.meldPointer] = type;
                this.meldClose[this.meldPointer] = close;
                ++this.meldPointer;
                break;
            case 1:
            default:
                this.handTiles[tile] += 3;
                if(close == 1) {
                    this.handOpenTiles[tile] += 3;

                    for(o = 0; o < 3; ++o) {
                        this.handOpenDisp[this.curOpenTile] = tile;
                        ++this.curOpenTile;
                    }
                }

                if(close == 0) {
                    this.handClosedTiles[tile] += 3;
                }

                this.meldTile[this.meldPointer] = tile;
                this.meldType[this.meldPointer] = type;
                this.meldClose[this.meldPointer] = close;
                ++this.meldPointer;
                break;
            case 2:
                this.handTiles[tile] += 4;
                if(close == 1) {
                    this.handOpenTiles[tile] += 4;

                    for(o = 0; o < 4; ++o) {
                        this.handOpenDisp[this.curOpenTile] = tile;
                        ++this.curOpenTile;
                    }
                }

                if(close == 0) {
                    this.closedKans[this.numKans] = tile;
                }

                ++this.numKans;
                this.meldTile[this.meldPointer] = tile;
                this.meldType[this.meldPointer] = type;
                this.meldClose[this.meldPointer] = close;
                ++this.meldPointer;
                break;
            case 3:
                this.handTiles[tile] += 2;
                if(close == 1) {
                    this.handOpenTiles[tile] += 2;

                    for(o = 0; o < 2; ++o) {
                        this.handOpenDisp[this.curOpenTile] = tile;
                        ++this.curOpenTile;
                    }
                }

                if(close == 0) {
                    this.handClosedTiles[tile] += 2;
                }

                this.meldTile[4] = tile;
                this.meldType[4] = type;
                this.meldClose[4] = close;
        }

    }

    public void setWaits() {
        int i;
        int j;
        int k;
        for(i = 0; i <= 4; ++i) {
            if(this.meldType[i] == 0 && this.meldClose[i] == 0) {
                this.isMeldChecked[i] = true;

                for(j = 0; j <= 4; ++j) {
                    if(!this.isMeldChecked[j] && this.meldType[j] == 0 && this.meldClose[j] == 0 && this.meldTile[j] == this.meldTile[i]) {
                        this.isMeldChecked[j] = true;

                        for(k = 0; k <= 4; ++k) {
                            if(!this.isMeldChecked[k] && this.meldType[k] == 0 && this.meldClose[k] == 0 && this.meldTile[k] == this.meldTile[i] && this.meldTile[k] == this.meldTile[j]) {
                                this.isMeldChecked[k] = true;
                                this.meldType[i] = this.meldType[j] = this.meldType[k] = 1;
                                ++this.meldTile[j];
                                this.meldTile[k] += 2;
                            }
                        }
                    }
                }
            }
        }

        for(i = 0; i < this.isMeldChecked.length; ++i) {
            this.isMeldChecked[i] = false;
        }

        for(i = 0; i <= 4; ++i) {
            if(this.meldType[i] == 1 && this.meldClose[i] == 0 && this.meldTile[i] < 25 && this.meldTile[i] % 9 < 7) {
                this.isMeldChecked[i] = true;

                for(j = 0; j <= 4; ++j) {
                    if(!this.isMeldChecked[j] && this.meldType[j] == 1 && this.meldClose[j] == 0 && this.meldTile[j] == this.meldTile[i] + 1) {
                        this.isMeldChecked[j] = true;

                        for(k = 0; k <= 4; ++k) {
                            if(!this.isMeldChecked[k] && this.meldType[k] == 1 && this.meldClose[k] == 0 && this.meldTile[k] == this.meldTile[i] + 2) {
                                this.isMeldChecked[k] = true;

                                int m;
                                for(m = 0; m <= 4; ++m) {
                                    if(!this.isMeldChecked[m] && this.meldType[m] == 0 && this.meldClose[m] == 0 && this.meldTile[m] == this.meldTile[i]) {
                                        this.meldType[i] = this.meldType[j] = this.meldType[k] = 0;
                                        --this.meldTile[j];
                                        this.meldTile[k] -= 2;
                                    }
                                }

                                if(this.meldType[i] == 1) {
                                    for(m = 0; m < this.isMeldChecked.length; ++m) {
                                        this.isMeldChecked[m] = false;
                                    }

                                    this.isMeldChecked[i] = this.isMeldChecked[j] = this.isMeldChecked[k] = true;
                                    this.isMeldPossible = true;

                                    for(m = 0; m <= 4; ++m) {
                                        if(!this.isMeldChecked[m]) {
                                            if(this.meldType[m] == 0) {
                                                this.isMeldPossible = this.isMeldPossible && (this.meldTile[m] % 9 == 0 || this.meldTile[m] % 9 == 6);
                                            } else if(this.meldTile[m] < 27) {
                                                this.isMeldPossible = this.isMeldPossible && (this.meldTile[m] % 9 == 0 || this.meldTile[m] % 9 == 8);
                                            }

                                            this.isMeldPossible = this.isMeldPossible && this.meldClose[m] == 0;
                                            if(this.meldType[m] == 1) {
                                                this.isMeldPossible = false;
                                            }
                                        }
                                    }

                                    if(this.isMeldPossible) {
                                        this.meldType[i] = this.meldType[j] = this.meldType[k] = 0;
                                        --this.meldTile[j];
                                        this.meldTile[k] -= 2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(i = 0; i < this.isMeldChecked.length; ++i) {
            this.isMeldChecked[i] = false;
        }

        if(this.numMelds == 7) {
            this.meldPointer = -1;

            for(i = 0; i < this.handClosedTiles.length - 7; ++i) {
                if(this.handClosedTiles[i] == 2 && this.handClosedTiles[i] % 9 < 7 && this.handClosedTiles[i + 1] == 2 && this.handClosedTiles[i + 2] == 2) {
                    if(this.meldPointer == -1) {
                        this.meldPointer = i;
                    } else if(i > this.meldPointer + 2) {
                        this.meldType[0] = this.meldType[1] = this.meldType[2] = this.meldType[3] = 0;
                        this.meldTile[0] = this.meldTile[1] = this.meldPointer;
                        this.meldTile[2] = this.meldTile[3] = i;
                        this.numMelds = 4;
                        i = this.handClosedTiles.length;
                    }
                }
            }

            if(this.numMelds == 4) {
                for(i = 0; i < this.handClosedTiles.length; ++i) {
                    if(this.handClosedTiles[i] == 2 && i != this.meldTile[0] && i != this.meldTile[0] + 1 && i != this.meldTile[0] + 2 && i != this.meldTile[2] && i != this.meldTile[2] + 1 && i != this.meldTile[2] + 2) {
                        this.meldTile[4] = i;
                        i = this.handClosedTiles.length;
                    }
                }
            }
        }

        if(this.agariHai == -1) {
            label398:
            switch(this.waitType) {
                case 2:
                    this.meldPointer = this.rand.nextInt(4);
                    i = 0;

                    while(true) {
                        if(i >= 4) {
                            break label398;
                        }

                        if(this.meldType[this.meldPointer] == 0 && this.meldClose[this.meldPointer] == 0) {
                            i = 5;
                            if(this.meldTile[this.meldPointer] % 9 == 0) {
                                this.agariHai = this.meldTile[this.meldPointer];
                                --this.handClosedTiles[this.agariHai];
                            } else if(this.meldTile[this.meldPointer] % 9 == 6) {
                                this.agariHai = this.meldTile[this.meldPointer] + 2;
                                --this.handClosedTiles[this.agariHai];
                            } else {
                                this.agariHai = this.meldTile[this.meldPointer];
                                if(this.rand.nextInt(2) == 1) {
                                    this.agariHai = this.meldTile[this.meldPointer] + 2;
                                }

                                --this.handClosedTiles[this.agariHai];
                            }
                        } else {
                            ++this.meldPointer;
                            if(this.meldPointer > 3) {
                                this.meldPointer = 0;
                            }
                        }

                        ++i;
                    }
                case 3:
                    this.meldPointer = this.rand.nextInt(4);
                    i = 0;

                    while(true) {
                        if(i >= 4) {
                            break label398;
                        }

                        if(this.meldType[this.meldPointer] == 1 && this.meldClose[this.meldPointer] == 0) {
                            i = 5;
                            this.agariHai = this.meldTile[this.meldPointer];
                            --this.handClosedTiles[this.agariHai];
                        } else {
                            ++this.meldPointer;
                            if(this.meldPointer > 3) {
                                this.meldPointer = 0;
                            }
                        }

                        ++i;
                    }
                case 4:
                    this.agariHai = this.meldTile[4];
                    --this.handClosedTiles[this.agariHai];
                    break;
                default:
                    do {
                        this.agariHai = this.rand.nextInt(34);
                    } while(this.handClosedTiles[this.agariHai] == 0);

                    --this.handClosedTiles[this.agariHai];
            }
        }

        if(this.agariHai == -1) {
            do {
                this.agariHai = this.rand.nextInt(34);
            } while(this.handClosedTiles[this.agariHai] == 0);

            --this.handClosedTiles[this.agariHai];
            this.waitType = 0;
        }

        if(this.waitType == 0) {
            for(i = 0; i < 4; ++i) {
                if(this.meldType[i] == 0 && this.meldClose[i] == 0) {
                    if(this.meldTile[i] % 9 == 0) {
                        if(this.agariHai == this.meldTile[i]) {
                            this.waitType = 2;
                            this.isRyanmen = true;
                            i = 5;
                        }
                    } else if(this.meldTile[i] % 9 == 6) {
                        if(this.agariHai == this.meldTile[i] + 2) {
                            this.waitType = 2;
                            this.isRyanmen = true;
                            i = 5;
                        }
                    } else if(this.agariHai == this.meldTile[i] || this.agariHai == this.meldTile[i] + 2) {
                        this.waitType = 2;
                        this.isRyanmen = true;
                        i = 5;
                    }
                }
            }

            for(i = 0; i < 4; ++i) {
                if(this.meldType[i] == 1 && this.meldClose[i] == 0 && this.agariHai == this.meldTile[i]) {
                    this.waitType = 3;
                    i = 5;
                }
            }

            if(this.agariHai == this.meldTile[4]) {
                this.waitType = 4;
            }

            for(i = 0; i < 4; ++i) {
                if(this.meldType[i] == 0 && this.meldClose[i] == 0 && this.agariHai == this.meldTile[i] + 1) {
                    this.waitType = 1;
                    i = 5;
                }
            }

            for(i = 0; i < 4; ++i) {
                if(this.meldType[i] == 0 && this.meldClose[i] == 0) {
                    if(this.meldTile[i] % 9 == 0 && this.agariHai == this.meldTile[i] + 2) {
                        this.waitType = 0;
                        i = 5;
                    } else if(this.meldTile[i] % 9 == 6 && this.agariHai == this.meldTile[i]) {
                        this.waitType = 0;
                        i = 5;
                    }
                }
            }
        }

        if(this.waitType == 2 || this.waitType == 4) {
            for(i = 0; i < 4; ++i) {
                if(this.meldType[i] == 0 && this.meldClose[i] == 0) {
                    this.isMeldChecked[i] = true;
                    if(this.meldTile[i] == this.meldTile[4] - 3 && this.meldTile[4] % 9 != 0 || this.meldTile[i] == this.meldTile[4] + 1 && this.meldTile[4] % 9 != 8) {
                        for(j = 0; j <= 4; ++j) {
                            if(!this.isMeldChecked[j] && this.meldType[j] == 0 && this.meldClose[j] == 0 && this.meldTile[j] == this.meldTile[i]) {
                                this.isMeldChecked[j] = true;
                                if(this.meldTile[i] == this.meldTile[4] - 3 && this.agariHai == this.meldTile[i] || this.meldTile[i] == this.meldTile[4] + 1 && this.agariHai == this.meldTile[i] + 3) {
                                    this.isRyanmen = true;
                                    this.waitType = 4;
                                    j = 5;
                                    i = 5;
                                } else if(this.agariHai == this.meldTile[4]) {
                                    this.isRyanmen = true;
                                    this.waitType = 4;
                                    j = 5;
                                    i = 5;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(i = 0; i < this.isMeldChecked.length; ++i) {
            this.isMeldChecked[i] = false;
        }

    }

    public void countFu() {
        if(this.handHan >= 5) {
            if(this.handHan < 6) {
                this.answerString2 = this.answerString2 + "Mangan\n";
            } else if(this.handHan < 8) {
                this.answerString2 = this.answerString2 + "Haneman\n";
            } else if(this.handHan < 11) {
                this.answerString2 = this.answerString2 + "Baiman\n";
            } else if(this.handHan < 13) {
                this.answerString2 = this.answerString2 + "Sanbaiman\n";
            } else {
                this.answerString2 = this.answerString2 + "Yakuman\n";
            }

        } else if(this.numMelds == 7) {
            this.handFu = this.unroundedFu = 25;
            this.answerString2 = this.answerString2 + "25 - Chiitoitsu\n";
        } else {
            this.unroundedFu = 20;
            this.answerString2 = this.answerString2 + "20 - Fuutei\n";
            if(this.isClosed && this.ronWind < 4) {
                this.unroundedFu += 10;
                this.answerString2 = this.answerString2 + "10 - Menzen ron\n";
            }

            if(this.ronWind == 4 && !this.handYaku[6]) {
                this.unroundedFu += 2;
                this.answerString2 = this.answerString2 + "2 - Tsumo\n";
            }

            int i;
            for(i = 0; i < this.isMeldChecked.length; ++i) {
                this.isMeldChecked[i] = false;
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 1 && this.meldTile[i] < 27 && this.meldClose[i] == 1 && this.meldTile[i] % 9 > 0 && this.meldTile[i] % 9 < 8) {
                    this.unroundedFu += 2;
                    this.answerString2 = this.answerString2 + "2 - Tanyao minkou\n";
                    this.isMeldChecked[i] = true;
                }
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 1 && this.meldClose[i] == 1) {
                    if(this.meldTile[i] < 27) {
                        if(this.meldTile[i] % 9 == 0 || this.meldTile[i] % 9 == 8) {
                            this.unroundedFu += 4;
                            this.answerString2 = this.answerString2 + "4 - Yao chuu minkou\n";
                            this.isMeldChecked[i] = true;
                        }
                    } else {
                        this.unroundedFu += 4;
                        this.answerString2 = this.answerString2 + "4 - Yao chuu minkou\n";
                        this.isMeldChecked[i] = true;
                    }
                }
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 1 && this.meldTile[i] < 27 && this.meldClose[i] == 0 && this.meldTile[i] % 9 > 0 && this.meldTile[i] % 9 < 8) {
                    this.unroundedFu += 4;
                    this.answerString2 = this.answerString2 + "4 - Tanyao ankou\n";
                    this.isMeldChecked[i] = true;
                }
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 1 && this.meldClose[i] == 0) {
                    if(this.meldTile[i] < 27) {
                        if(this.meldTile[i] % 9 == 0 || this.meldTile[i] % 9 == 8) {
                            this.unroundedFu += 8;
                            this.answerString2 = this.answerString2 + "8 - Yao chuu ankou\n";
                            this.isMeldChecked[i] = true;
                        }
                    } else {
                        this.unroundedFu += 8;
                        this.answerString2 = this.answerString2 + "8 - Yao chuu ankou\n";
                        this.isMeldChecked[i] = true;
                    }
                }
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 2 && this.meldTile[i] < 27 && this.meldClose[i] == 1 && this.meldTile[i] % 9 > 0 && this.meldTile[i] % 9 < 8) {
                    this.unroundedFu += 8;
                    this.answerString2 = this.answerString2 + "8 - Tan yao minkan\n";
                    this.isMeldChecked[i] = true;
                }
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 2 && this.meldClose[i] == 1) {
                    if(this.meldTile[i] < 27) {
                        if(this.meldTile[i] % 9 == 0 || this.meldTile[i] % 9 == 8) {
                            this.unroundedFu += 16;
                            this.answerString2 = this.answerString2 + "16 - Yao chuu minkan\n";
                            this.isMeldChecked[i] = true;
                        }
                    } else {
                        this.unroundedFu += 16;
                        this.answerString2 = this.answerString2 + "16 - Yao chuu minkan\n";
                        this.isMeldChecked[i] = true;
                    }
                }
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 2 && this.meldTile[i] < 27 && this.meldClose[i] == 0 && this.meldTile[i] % 9 > 0 && this.meldTile[i] % 9 < 8) {
                    this.unroundedFu += 16;
                    this.answerString2 = this.answerString2 + "16 - Tan yao ankan\n";
                    this.isMeldChecked[i] = true;
                }
            }

            for(i = 0; i < 4; ++i) {
                if(!this.isMeldChecked[i] && this.meldType[i] == 2 && this.meldClose[i] == 0) {
                    if(this.meldTile[i] < 27) {
                        if(this.meldTile[i] % 9 == 0 || this.meldTile[i] % 9 == 8) {
                            this.unroundedFu += 32;
                            this.answerString2 = this.answerString2 + "32 - Yao chuu ankan\n";
                            this.isMeldChecked[i] = true;
                        }
                    } else {
                        this.unroundedFu += 32;
                        this.answerString2 = this.answerString2 + "32 - Yao chuu ankan\n";
                        this.isMeldChecked[i] = true;
                    }
                }
            }

            for(i = 0; i < this.isMeldChecked.length; ++i) {
                this.isMeldChecked[i] = false;
            }

            if(this.meldTile[4] > 26 && this.meldTile[4] < 30) {
                this.unroundedFu += 2;
                this.answerString2 = this.answerString2 + "2 - Sangenpai jantou\n";
            }

            if(this.meldTile[4] == 30 + this.seatWind) {
                this.unroundedFu += 2;
                this.answerString2 = this.answerString2 + "2 - Seat wind jantou\n";
            }

            if(this.meldTile[4] == 30 + this.roundWind) {
                this.unroundedFu += 2;
                this.answerString2 = this.answerString2 + "2 - Round wind jantou\n";
            }

            if(!this.handYaku[6]) {
                switch(this.waitType) {
                    case 0:
                        this.unroundedFu += 2;
                        this.answerString2 = this.answerString2 + "2 - Penchan wait\n";
                        break;
                    case 1:
                        this.unroundedFu += 2;
                        this.answerString2 = this.answerString2 + "2 - Kanchan wait\n";
                        break;
                    case 2:
                        this.answerString2 = this.answerString2 + "0 - Ryanmen wait\n";
                        break;
                    case 3:
                        this.answerString2 = this.answerString2 + "0 - Shanpon wait\n";
                        break;
                    case 4:
                    default:
                        this.unroundedFu += 2;
                        this.answerString2 = this.answerString2 + "2 - Tanki wait\n";
                }
            } else {
                this.answerString2 = this.answerString2 + "0 - Ryanmen wait\n";
            }

            if(this.handHan == 1 && this.unroundedFu == 20 && !this.handYaku[6]) {
                this.unroundedFu += 10;
                this.answerString2 = this.answerString2 + "10 - Open pinfu\n";
            }

            this.answerString2 = this.answerString2 + "==========\n";
            this.answerString2 = this.answerString2 + this.unroundedFu + "\n";
            this.handFu = 10 * (int)Math.ceil((double)this.unroundedFu / 10.0D);
            this.answerString2 = this.answerString2 + "->" + this.handFu + "\n";
        }
    }

    public void countYakuman() {
        if(this.handYaku[12] && this.ronWind < 4) {
            ++this.numYakuman;
            this.answerString = this.answerString + this.yakumanStrings[14] + "\n";
        }

        if(this.numMelds == 13) {
            if(this.handTiles[this.agariHai] == 2) {
                this.numYakuman += 2;
                this.answerString = this.answerString + this.yakumanStrings[8] + "\n";
            } else {
                ++this.numYakuman;
                this.answerString = this.answerString + this.yakumanStrings[7] + "\n";
            }
        }

        int i;
        if(this.isClosed) {
            this.isMeldPossible = true;

            for(i = 10; i < 17; ++i) {
                this.isMeldPossible = this.isMeldPossible && this.handTiles[i] == 2;
            }

            /* house rule (chariot), make it impossible to earn
            if(this.isMeldPossible) {
                ++this.numYakuman;
                this.answerString = this.answerString + this.yakumanStrings[5] + "\n";
            }*/
        }

        if(this.isClosed) {
            for(i = 0; i < 3; ++i) {
                this.isMeldPossible = true;

                for(int j = 0; j < 9; ++j) {
                    if(j != 0 && j != 8) {
                        this.isMeldPossible = this.isMeldPossible && this.handTiles[j + 9 * i] >= 1;
                    } else {
                        this.isMeldPossible = this.isMeldPossible && this.handTiles[j + 9 * i] >= 3;
                    }
                }

                if(this.isMeldPossible) {
                    if(this.agariHai != 0 + 9 * i && this.agariHai != 8 + 9 * i) {
                        if(this.handTiles[this.agariHai] == 2) {
                            this.numYakuman += 2;
                            this.answerString = this.answerString + this.yakumanStrings[13] + "\n";
                        } else {
                            ++this.numYakuman;
                            this.answerString = this.answerString + this.yakumanStrings[12] + "\n";
                        }
                    } else if(this.handTiles[this.agariHai] == 4) {
                        this.numYakuman += 2;
                        this.answerString = this.answerString + this.yakumanStrings[13] + "\n";
                    } else {
                        ++this.numYakuman;
                        this.answerString = this.answerString + this.yakumanStrings[12] + "\n";
                    }
                }
            }
        }

        this.meldPointer = 0;

        for(i = 0; i < 4; ++i) {
            if(this.meldTile[i] == 27) {
                this.meldPointer += 3;
            }

            if(this.meldTile[i] == 28) {
                this.meldPointer += 5;
            }

            if(this.meldTile[i] == 29) {
                this.meldPointer += 7;
            }
        }

        if(this.meldPointer == 15) {
            ++this.numYakuman;
            this.answerString = this.answerString + this.yakumanStrings[0] + "\n";
        }

        this.meldPointer = 0;

        for(i = 0; i < 4; ++i) {
            if((this.meldType[i] == 1 || this.meldType[i] == 2) && this.meldClose[i] == 0) {
                ++this.meldPointer;
            }
        }

        if(this.meldPointer == 4) {
            if(this.waitType == 4) {
                this.numYakuman += 2;
                this.answerString = this.answerString + this.yakumanStrings[2] + "\n";
            } else if(this.ronWind == 4) {
                ++this.numYakuman;
                this.answerString = this.answerString + this.yakumanStrings[1] + "\n";
            }
        }

        this.isMeldPossible = true;

        for(i = 0; i < 27; ++i) {
            this.isMeldPossible = this.isMeldPossible && this.handTiles[i] == 0;
        }

        if(this.isMeldPossible) {
            ++this.numYakuman;
            this.answerString = this.answerString + this.yakumanStrings[3] + "\n";
        }

        this.isMeldPossible = true;

        for(i = 0; i < this.handTiles.length; ++i) {
            if(i == 19) {
                i = 22;
            }

            if(i == 23) {
                i = 24;
            }

            if(i == 25) {
                i = 26;
            }

            if(i == 28) {
                i = 29;
            }

            this.isMeldPossible = this.isMeldPossible && this.handTiles[i] == 0;
        }

        if(this.isMeldPossible) {
            ++this.numYakuman;
            this.answerString = this.answerString + this.yakumanStrings[4] + "\n";
        }

        this.isMeldPossible = true;

        for(i = 0; i < this.handTiles.length; ++i) {
            if(i < 27 && i % 9 == 8) {
                ++i;
            }

            if(i < 27 && i % 9 == 0) {
                ++i;
            }

            this.isMeldPossible = this.isMeldPossible && this.handTiles[i] == 0;
        }

        if(this.isMeldPossible) {
            ++this.numYakuman;
            this.answerString = this.answerString + this.yakumanStrings[6] + "\n";
        }

        this.meldPointer = 0;

        for(i = 0; i < 4; ++i) {
            if(this.meldTile[i] > 29) {
                ++this.meldPointer;
            }
        }

        if(this.meldPointer == 3) {
            if(this.meldTile[4] > 29) {
                ++this.numYakuman;
                this.answerString = this.answerString + this.yakumanStrings[9] + "\n";
            }
        } else if(this.meldPointer == 4) {
            this.numYakuman += 2;
            this.answerString = this.answerString + this.yakumanStrings[10] + "\n";
        }

        this.meldPointer = 0;

        for(i = 0; i < 4; ++i) {
            if(this.meldType[i] == 2) {
                ++this.meldPointer;
            }
        }

        if(this.meldPointer == 4) {
            ++this.numYakuman;
            this.answerString = this.answerString + this.yakumanStrings[11] + "\n";
        }

    }

    public void countYaku() {
        if(this.isRiichi && !this.handYaku[12]) {
            this.handYaku[0] = true;
        }

        if(this.isClosed && this.ronWind == 4) {
            this.handYaku[1] = true;
        }

        this.meldPointer = 0;

        int i;
        for(i = 0; i < 4; ++i) {
            if(this.meldTile[i] == 30 + this.roundWind) {
                this.handYaku[2] = true;
            }

            if(this.meldTile[i] == 30 + this.seatWind) {
                this.handYaku[3] = true;
            }

            if(this.meldTile[i] >= 27 && this.meldTile[i] <= 29) {
                ++this.meldPointer;
                this.handYaku[4] = true;
            }
        }

        if(this.meldPointer == 2 && this.meldTile[4] >= 27 && this.meldTile[4] <= 29) {
            this.handYaku[16] = true;
        }

        this.isMeldPossible = this.handTiles[0] == 0 && this.handTiles[8] == 0 && this.handTiles[9] == 0 && this.handTiles[17] == 0 && this.handTiles[18] == 0 && this.handTiles[26] == 0;

        for(i = 27; i < 34; ++i) {
            this.isMeldPossible = this.isMeldPossible && this.handTiles[i] == 0;
        }

        this.handYaku[5] = this.isMeldPossible;
        if(this.isClosed) {
            this.isMeldPossible = true;

            for(i = 0; i < 4; ++i) {
                this.isMeldPossible = this.isMeldPossible && this.meldType[i] == 0 && this.meldClose[i] == 0;
            }

            this.isMeldPossible = this.isMeldPossible && (this.meldTile[4] < 27 || this.meldTile[4] > 29);
            this.isMeldPossible = this.isMeldPossible && this.meldTile[4] != 30 + this.roundWind && this.meldTile[4] != 30 + this.seatWind;
            this.isRyanmen = this.isRyanmen || this.waitType == 2;
            this.isMeldPossible = this.isMeldPossible && this.isRyanmen;
            this.handYaku[6] = this.isMeldPossible;
        }

        for(i = 0; i < this.isMeldChecked.length; ++i) {
            this.isMeldChecked[i] = false;
        }

        int j;
        if(this.isClosed) {
            this.meldPointer = 0;

            for(i = 0; i <= 4; ++i) {
                if(this.meldType[i] == 0 && this.meldClose[i] == 0 && !this.isMeldChecked[i]) {
                    for(j = 0; j <= 4; ++j) {
                        if(!this.isMeldChecked[j] && j != i && this.meldType[j] == 0 && this.meldClose[j] == 0 && this.meldTile[j] == this.meldTile[i]) {
                            this.isMeldChecked[j] = this.isMeldChecked[i] = true;
                            ++this.meldPointer;
                            j = 5;
                        }
                    }
                }
            }

            if(this.meldPointer == 2) {
                this.handYaku[21] = true;
            }

            if(this.meldPointer == 1) {
                this.handYaku[7] = true;
            }

            for(i = 0; i < this.isMeldChecked.length; ++i) {
                this.isMeldChecked[i] = false;
            }
        }

        this.isMeldPossible = true;

        for(i = 0; i < 27; ++i) {
            if(i % 9 == 8) {
                ++i;
            }

            if(i % 9 == 0) {
                ++i;
            }

            if(i < 27) {
                this.isMeldPossible = this.isMeldPossible && this.handTiles[i] == 0;
            }
        }

        if(this.isMeldPossible) {
            this.handYaku[17] = true;
        } else {
            this.isMeldPossible = this.numMelds != 7;

            for(i = 0; i <= 4; ++i) {
                if(this.meldType[i] == 0) {
                    this.isMeldPossible = this.isMeldPossible && (this.meldTile[i] % 9 == 0 || this.meldTile[i] % 9 == 6);
                } else if(this.meldTile[i] < 27) {
                    this.isMeldPossible = this.isMeldPossible && (this.meldTile[i] % 9 == 0 || this.meldTile[i] % 9 == 8);
                }
            }

            if(this.isMeldPossible) {
                this.meldPointer = 0;

                for(i = 27; i < 34; ++i) {
                    this.meldPointer += this.handTiles[i];
                }

                if(this.meldPointer > 0) {
                    this.handYaku[8] = true;
                } else {
                    this.handYaku[19] = true;
                }
            }
        }

        int k;
        for(i = 0; i < 4; ++i) {
            if(this.meldType[i] == 0 && this.meldTile[i] % 9 == 0) {
                for(j = 0; j < 4; ++j) {
                    if(this.meldType[j] == 0 && this.meldTile[j] == this.meldTile[i] + 3) {
                        for(k = 0; k < 4; ++k) {
                            if(this.meldType[k] == 0 && this.meldTile[k] == this.meldTile[j] + 3) {
                                this.handYaku[9] = true;
                                k = 5;
                                j = 5;
                                i = 5;
                            }
                        }
                    }
                }
            }
        }

        for(i = 0; i < 4; ++i) {
            this.meldPointer = 0;
            if(this.meldTile[i] < 9) {
                if(this.meldType[i] > 0) {
                    this.meldPointer = 1;
                }

                for(j = 0; j < 4; ++j) {
                    if((this.meldType[j] + this.meldType[i] >= 3 || this.meldType[j] == this.meldType[i]) && this.meldTile[j] == this.meldTile[i] + 9) {
                        for(k = 0; k < 4; ++k) {
                            if((this.meldType[k] + this.meldType[i] >= 3 || this.meldType[k] == this.meldType[i]) && this.meldTile[k] == this.meldTile[j] + 9) {
                                if(this.meldPointer > 0) {
                                    this.handYaku[11] = true;
                                } else {
                                    this.handYaku[10] = true;
                                }

                                k = 5;
                                j = 5;
                                i = 5;
                            }
                        }
                    }
                }
            }
        }

        this.meldPointer = 0;

        for(i = 0; i < 4; ++i) {
            if(this.meldType[i] == 2) {
                ++this.meldPointer;
            }
        }

        if(this.meldPointer >= 3) {
            this.handYaku[13] = true;
        }

        this.isMeldPossible = true;

        for(i = 0; i < 4; ++i) {
            this.isMeldPossible = this.isMeldPossible && (this.meldType[i] == 2 || this.meldType[i] == 1);
        }

        if(this.isMeldPossible) {
            this.handYaku[14] = true;
        }

        this.meldPointer = 0;

        for(i = 0; i < 4; ++i) {
            //for san ankou, the only triplets that count are closed triplets that are not completed
            //due to ron
            if((this.meldType[i] == 2 || this.meldType[i] == 1) && this.meldClose[i] == 0) {
                //the triplet doesn't count if completed by ron.
                //i.e. this triplet only counts if they won on tsumo or this meld wasn't completed using the winning tile
                if (this.ronWind == 4 || //win on tsumo, or...
                        this.agariHai != this.meldTile[i] /*winning tile wasn't part of this meld*/) {
                    ++this.meldPointer;
                }
            }
        }

        if(this.meldPointer >= 3) {
            this.handYaku[15] = true;
        }

        if(this.numMelds == 7) {
            this.handYaku[18] = true;
        }

        this.isMeldPossible = true;
        this.arrayPointer = this.meldPointer = 0;

        for(i = 0; i < 3; ++i) {
            this.arrayPointer = 0;

            for(j = 0; j < 9; ++j) {
                this.arrayPointer += this.handTiles[j + 9 * i];
            }

            if(this.meldPointer == 0) {
                this.meldPointer = this.arrayPointer;
            } else if(this.arrayPointer > 0) {
                this.isMeldPossible = false;
            }
        }

        if(this.isMeldPossible) {
            this.meldPointer = 0;

            for(i = 27; i < 34; ++i) {
                this.meldPointer += this.handTiles[i];
            }

            if(this.meldPointer > 0) {
                this.handYaku[20] = true;
            } else {
                this.handYaku[22] = true;
            }
        }

    }

    public void countHan() {
        boolean hanValue = false;

        for(int i = 0; i < this.handYaku.length; ++i) {
            if(this.handYaku[i]) {
                int var4;
                var4 = 0;
                label68:
                switch(i) {
                    case 0:
                    default:
                        var4 = 1;
                        break;
                    case 1:
                        var4 = 1;
                        break;
                    case 2:
                        var4 = 1;
                        break;
                    case 3:
                        var4 = 1;
                        break;
                    case 4:
                        int j = 0;

                        while(true) {
                            if(j >= 4) {
                                break label68;
                            }

                            if(this.meldTile[j] >= 27 && this.meldTile[j] <= 29) {
                                ++var4;
                            }

                            ++j;
                        }
                    case 5:
                        var4 = 1;
                        break;
                    case 6:
                        var4 = 1;
                        break;
                    case 7:
                        var4 = 1;
                        break;
                    case 8:
                        var4 = 2;
                        if(!this.isClosed) {
                            --var4;
                        }
                        break;
                    case 9:
                        var4 = 2;
                        if(!this.isClosed) {
                            --var4;
                        }
                        break;
                    case 10:
                        var4 = 2;
                        if(!this.isClosed) {
                            --var4;
                        }
                        break;
                    case 11:
                        var4 = 2;
                        break;
                    case 12:
                        var4 = 2;
                        break;
                    case 13:
                        var4 = 2;
                        break;
                    case 14:
                        var4 = 2;
                        break;
                    case 15:
                        var4 = 2;
                        break;
                    case 16:
                        var4 = 2;
                        break;
                    case 17:
                        var4 = 2;
                        break;
                    case 18:
                        var4 = 2;
                        break;
                    case 19:
                        var4 = 3;
                        if(!this.isClosed) {
                            --var4;
                        }
                        break;
                    case 20:
                        var4 = 3;
                        if(!this.isClosed) {
                            --var4;
                        }
                        break;
                    case 21:
                        var4 = 3;
                        break;
                    case 22:
                        var4 = 6;
                        if(!this.isClosed) {
                            --var4;
                        }
                }

                this.handHan += var4;
                this.answerString = this.answerString + var4 + " - " + this.yakuStrings[i] + "\n";
            }
        }

        if(this.numDora > 0) {
            this.handHan += this.numDora;
            this.answerString = this.answerString + this.numDora + " - Dora\n";
        }

        this.answerString = this.answerString + "==========\n" + this.handHan + "\n";
    }

    public void countPayments() {
        if(this.numYakuman > 0) {
            this.basePay = this.numYakuman * 8000;
        } else if(this.handHan >= 5) {
            if(this.handHan < 6) {
                this.basePay = 2000;
            } else if(this.handHan < 8) {
                this.basePay = 3000;
            } else if(this.handHan < 11) {
                this.basePay = 4000;
            } else if(this.handHan < 13) {
                this.basePay = 6000;
            } else {
                this.basePay = 8000;
            }
        } else {
            this.basePay = this.handFu * 4;

            for(int i = 0; i < this.handHan; ++i) {
                this.basePay *= 2;
            }

            if(this.basePay > 2000) {
                this.basePay = 2000;
            }
        }

        if(this.seatWind == 0) {
            this.basePay *= 2;
            if(this.ronWind < 4) {
                this.basePay *= 3;
                this.basePay = 100 * (int)Math.ceil((double)this.basePay / 100.0D);
                this.handPayKo = this.basePay;
            } else {
                this.basePay = 100 * (int)Math.ceil((double)this.basePay / 100.0D);
                this.handPayKo = this.basePay;
            }
        } else if(this.ronWind < 4) {
            this.basePay *= 4;
            this.basePay = 100 * (int)Math.ceil((double)this.basePay / 100.0D);
            if(this.ronWind == 0) {
                this.handPayOya = this.basePay;
            } else {
                this.handPayKo = this.basePay;
            }
        } else {
            this.handPayKo = 100 * (int)Math.ceil((double)this.basePay / 100.0D);
            this.basePay *= 2;
            this.handPayOya = 100 * (int)Math.ceil((double)this.basePay / 100.0D);
        }

    }

    public void submitAnswer(View view) {
        submitButton.setEnabled(false);

        this.hanList.setText(this.answerString.trim());
        this.fuList.setText(this.answerString2.trim());
        this.fuAnswer.setText(Integer.toString(this.handFu));
        this.hanAnswer.setText(Integer.toString(this.handHan));
        this.koAnswer.setText(Integer.toString(this.handPayKo));
        this.oyaAnswer.setText(Integer.toString(this.handPayOya));
        this.meldPointer = this.arrayPointer = 0;

        try {
            this.arrayPointer = Integer.parseInt(this.fuEntry.getText().toString());
        } catch (NumberFormatException var5) {
            this.fuEntry.setText("0");
        }

        try {
            this.arrayPointer = Integer.parseInt(this.hanEntry.getText().toString());
        } catch (NumberFormatException var4) {
            this.hanEntry.setText("0");
        }

        try {
            this.arrayPointer = Integer.parseInt(this.koEntry.getText().toString());
        } catch (NumberFormatException var3) {
            this.koEntry.setText("0");
        }

        try {
            this.arrayPointer = Integer.parseInt(this.oyaEntry.getText().toString());
        } catch (NumberFormatException var2) {
            this.oyaEntry.setText("0");
        }

        this.meldPointer = Integer.parseInt(this.fuEntry.getText().toString());
        if(this.meldPointer != this.handFu && this.meldPointer != this.unroundedFu) {
            this.fuEntry.setTextColor(Color.RED);
            this.fuEntry.setBackgroundTintList(redColorStateList);
        } else {
            ++this.numCorFu;
            this.fuEntry.setTextColor(Color.GREEN);
            this.fuEntry.setBackgroundTintList(greenColorStateList);
        }

        this.meldPointer = Integer.parseInt(this.hanEntry.getText().toString());
        if(this.yakumanBox.isChecked()) {
            this.arrayPointer = 1;
        } else {
            this.arrayPointer = 0;
        }

        if(this.meldPointer == this.handHan) {
            this.hanEntry.setTextColor(Color.GREEN);
            this.hanEntry.setBackgroundTintList(greenColorStateList);
            if(this.isYakuman) {
                yakumanAnswerCheckBox.setChecked(true);
                if(this.arrayPointer == 1) {
                    ++this.numCorHan;
                    this.yakumanBox.setButtonTintList(greenColorStateList);
                } else {
                    this.yakumanBox.setButtonTintList(redColorStateList);
                }
            } else if(this.arrayPointer == 0) {
                yakumanAnswerCheckBox.setChecked(false);
                ++this.numCorHan;
                this.yakumanBox.setButtonTintList(greenColorStateList);
            } else {
                yakumanAnswerCheckBox.setChecked(false);
                this.yakumanBox.setButtonTintList(redColorStateList);
            }
        } else {
            this.hanEntry.setTextColor(Color.RED);
            this.hanEntry.setBackgroundTintList(redColorStateList);
            if(this.isYakuman) {
                yakumanAnswerCheckBox.setChecked(true);
                if(this.arrayPointer == 1) {
                    this.yakumanBox.setButtonTintList(greenColorStateList);
                } else {
                    this.yakumanBox.setButtonTintList(redColorStateList);
                }
            } else if(this.arrayPointer == 0) {
                yakumanAnswerCheckBox.setChecked(false);
                this.yakumanBox.setButtonTintList(greenColorStateList);
            } else {
                yakumanAnswerCheckBox.setChecked(false);
                this.yakumanBox.setButtonTintList(redColorStateList);
            }
        }

        this.meldPointer = Integer.parseInt(this.koEntry.getText().toString());
        this.arrayPointer = Integer.parseInt(this.oyaEntry.getText().toString());
        if(this.ronWind < 4) {
            if(this.meldPointer == this.handPayKo && this.arrayPointer == this.handPayOya || this.meldPointer == this.handPayOya && this.arrayPointer == this.handPayKo) {
                ++this.numCorPay;
                this.koEntry.setTextColor(Color.GREEN);
                this.koEntry.setBackgroundTintList(greenColorStateList);
                this.oyaEntry.setTextColor(Color.GREEN);
                this.oyaEntry.setBackgroundTintList(greenColorStateList);
            } else if(this.meldPointer == this.handPayKo) {
                this.koEntry.setTextColor(Color.GREEN);
                this.koEntry.setBackgroundTintList(greenColorStateList);
                this.oyaEntry.setTextColor(Color.RED);
                this.oyaEntry.setBackgroundTintList(redColorStateList);
            } else if(this.arrayPointer == this.handPayOya) {
                this.oyaEntry.setTextColor(Color.GREEN);
                this.oyaEntry.setBackgroundTintList(greenColorStateList);
                this.koEntry.setTextColor(Color.RED);
                this.koEntry.setBackgroundTintList(redColorStateList);
            } else {
                this.koEntry.setTextColor(Color.RED);
                this.koEntry.setBackgroundTintList(redColorStateList);
                this.oyaEntry.setTextColor(Color.RED);
                this.oyaEntry.setBackgroundTintList(redColorStateList);
            }
        } else if(this.meldPointer == this.handPayKo && this.arrayPointer == this.handPayOya) {
            ++this.numCorPay;
            this.koEntry.setTextColor(Color.GREEN);
            this.koEntry.setBackgroundTintList(greenColorStateList);
            this.oyaEntry.setTextColor(Color.GREEN);
            this.oyaEntry.setBackgroundTintList(greenColorStateList);
        } else if(this.meldPointer == this.handPayKo) {
            this.koEntry.setTextColor(Color.GREEN);
            this.koEntry.setBackgroundTintList(greenColorStateList);
            this.oyaEntry.setTextColor(Color.RED);
            this.oyaEntry.setBackgroundTintList(redColorStateList);
        } else if(this.arrayPointer == this.handPayOya) {
            this.oyaEntry.setTextColor(Color.GREEN);
            this.oyaEntry.setBackgroundTintList(greenColorStateList);
            this.koEntry.setTextColor(Color.RED);
            this.koEntry.setBackgroundTintList(redColorStateList);
        } else {
            this.koEntry.setTextColor(Color.RED);
            this.koEntry.setBackgroundTintList(redColorStateList);
            this.oyaEntry.setTextColor(Color.RED);
            this.oyaEntry.setBackgroundTintList(redColorStateList);
        }

        this.corFuField.setText(Integer.toString(this.numCorFu)); // Not including these numbers due to space constraints
        this.corHanField.setText(Integer.toString(this.numCorHan));
        this.corPayField.setText(Integer.toString(this.numCorPay));
    }

    public boolean sanityCheck(String s) {
        ++this.sanityCheck;
        if(this.sanityCheck == 100) {
            this.isError = true;
            this.answerString = this.answerString + "ERROR: " + s;

            int i;
            for(i = 0; i < this.handYaku.length; ++i) {
                if(this.handYaku[i]) {
                    this.answerString = this.answerString + i + ".";
                }
            }

            for(i = 0; i <= 4; ++i) {
                this.answerString = this.answerString + this.meldType[i] + "." + this.meldTile[i] + "." + this.meldClose[i];
                if(i < 4) {
                    this.answerString = this.answerString + ".";
                }
            }

            this.answerString = this.answerString + "\nPlease report this!\n";
            this.hanList.setTextColor(Color.RED);
            this.hanList.setText(this.answerString);
            this.sanityCheck = 0;
        }

        return false;
    }

    /*
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 110) {
            if(this.isDebugMode) {
                ++this.debugPointer;
                if(this.isDebugYakuman) {
                    if(this.debugPointer >= this.yakumanStrings.length) {
                        this.debugPointer = 0;
                    }

                    this.riichiField.setText(this.yakumanStrings[this.debugPointer]);
                } else {
                    if(this.debugPointer >= this.yakuStrings.length) {
                        this.debugPointer = 0;
                    }

                    this.riichiField.setText(this.yakuStrings[this.debugPointer]);
                }
            } else {
                this.isDebugMode = true;
                this.debugPointer = 0;
                this.riichiField.setText(this.yakuStrings[this.debugPointer]);
            }
        }

        if(e.getKeyChar() == 121) {
            this.isDebugYakuman = !this.isDebugYakuman;
            this.debugPointer = 0;
            if(this.isDebugMode) {
                if(this.isDebugYakuman) {
                    this.riichiField.setText(this.yakumanStrings[this.debugPointer]);
                } else {
                    this.riichiField.setText(this.yakuStrings[this.debugPointer]);
                }
            } else {
                this.isDebugMode = true;
                this.riichiField.setText(this.yakumanStrings[this.debugPointer]);
            }
        }

        if(e.getKeyChar() == 100) {
            this.isDebugSpecific = true;
            this.isDebugMode = true;
            this.riichiField.setText("Specific debug mode");
        }

        if(e.getKeyChar() == 119) {
            this.isMassDebug = true;
            this.riichiField.setText("Mass debug mode");
        }

    }

    public void keyPressed(KeyEvent arg0) {
    }

    public void keyReleased(KeyEvent arg0) {
    }

    public void actionPerformed(ActionEvent e) {
        if("New".equals(e.getActionCommand())) {
            if(!this.isMassDebug) {
                ++this.numHands;
                this.newHand();
            } else {
                int i;
                if(this.isDebugMode) {
                    for(i = 0; i < 9000; ++i) {
                        this.isDebugMode = true;
                        ++this.numHands;
                        ++this.numCorFu;
                        ++this.numCorHan;
                        ++this.numCorPay;
                        if(this.rand.nextInt(5) == 1) {
                            --this.numCorHan;
                        }

                        if(this.rand.nextInt(5) == 1) {
                            --this.numCorPay;
                        }

                        if(this.rand.nextInt(4) == 1) {
                            --this.numCorFu;
                        }

                        this.newHand();
                        if(this.isError) {
                            i = 9001;
                        }
                    }
                } else {
                    for(i = 0; i < 9000; ++i) {
                        ++this.numHands;
                        ++this.numCorFu;
                        ++this.numCorHan;
                        ++this.numCorPay;
                        if(this.rand.nextInt(5) == 1) {
                            --this.numCorHan;
                        }

                        if(this.rand.nextInt(5) == 1) {
                            --this.numCorPay;
                        }

                        if(this.rand.nextInt(4) == 1) {
                            --this.numCorFu;
                        }

                        this.newHand();
                        if(this.isError) {
                            i = 9001;
                        }
                    }
                }

                this.isMassDebug = false;
            }
        }

        if("Check".equals(e.getActionCommand()) && !this.isChecked) {
            this.isChecked = true;
            this.submitAnswer();
        }

        if("Yakuman".equals(e.getActionCommand())) {
            if(this.yakumanBox.isChecked()) {
                this.fuEntry.setEnabled(false);
                this.fuEntry.setText("0");
                this.hanEntry.setEnabled(false);
                this.hanEntry.setText("0");
            } else {
                this.fuEntry.setEnabled(true);
                this.hanEntry.setEnabled(true);
            }
        }

        if("Infure".equals(e.getActionCommand())) {
            this.isInfure = !this.infureBox.isChecked();
        }

    }

    public void focusGained(FocusEvent e) {
        this.fuEntry.setEnabled(true);
    }

    public void focusLost(FocusEvent e) {
        try {
            if(Integer.parseInt(this.hanEntry.getText()) >= 5) {
                this.fuEntry.setEnabled(false);
                this.fuEntry.setText("0");
            }
        } catch (NumberFormatException var3) {
            this.hanEntry.setText("0");
        }

    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseClicked(MouseEvent event) {
        this.repaint();
    }
    */
}
