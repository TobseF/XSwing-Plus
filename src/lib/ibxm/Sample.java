package lib.ibxm;

public class Sample {
    public String name = "";
    public boolean set_panning;
    public int volume;
    public int panning;
    public int c2_rate = 8363;
    public int relative_note;
    public int fine_tune;
    private int loop_start;
    private int loop_length;
    private short[] sample_data;
    private static final int POINT_SHIFT = 4;
    private static final int POINTS = 16;
    private static final int OVERLAP = 8;
    private static final int INTERP_SHIFT = 11;
    private static final int INTERP_BITMASK = 2047;
    private static final short[] sinc_table;

    static {
        short[] sArray = new short[272];
        sArray[1] = -7;
        sArray[2] = 27;
        sArray[3] = -71;
        sArray[4] = 142;
        sArray[5] = -227;
        sArray[6] = 299;
        sArray[7] = 32439;
        sArray[8] = 299;
        sArray[9] = -227;
        sArray[10] = 142;
        sArray[11] = -71;
        sArray[12] = 27;
        sArray[13] = -7;
        sArray[18] = -5;
        sArray[19] = 36;
        sArray[20] = -142;
        sArray[21] = 450;
        sArray[22] = -1439;
        sArray[23] = 32224;
        sArray[24] = 2302;
        sArray[25] = -974;
        sArray[26] = 455;
        sArray[27] = -190;
        sArray[28] = 64;
        sArray[29] = -15;
        sArray[30] = 2;
        sArray[33] = 6;
        sArray[34] = -33;
        sArray[35] = 128;
        sArray[36] = -391;
        sArray[37] = 1042;
        sArray[38] = -2894;
        sArray[39] = 31584;
        sArray[40] = 4540;
        sArray[41] = -1765;
        sArray[42] = 786;
        sArray[43] = -318;
        sArray[44] = 105;
        sArray[45] = -25;
        sArray[46] = 3;
        sArray[49] = 10;
        sArray[50] = -55;
        sArray[51] = 204;
        sArray[52] = -597;
        sArray[53] = 1533;
        sArray[54] = -4056;
        sArray[55] = 30535;
        sArray[56] = 6977;
        sArray[57] = -2573;
        sArray[58] = 1121;
        sArray[59] = -449;
        sArray[60] = 148;
        sArray[61] = -36;
        sArray[62] = 5;
        sArray[64] = -1;
        sArray[65] = 13;
        sArray[66] = -71;
        sArray[67] = 261;
        sArray[68] = -757;
        sArray[69] = 1916;
        sArray[70] = -4922;
        sArray[71] = 29105;
        sArray[72] = 9568;
        sArray[73] = -3366;
        sArray[74] = 1448;
        sArray[75] = -578;
        sArray[76] = 191;
        sArray[77] = -47;
        sArray[78] = 7;
        sArray[80] = -1;
        sArray[81] = 15;
        sArray[82] = -81;
        sArray[83] = 300;
        sArray[84] = -870;
        sArray[85] = 2185;
        sArray[86] = -5498;
        sArray[87] = 27328;
        sArray[88] = 12263;
        sArray[89] = -4109;
        sArray[90] = 1749;
        sArray[91] = -698;
        sArray[92] = 232;
        sArray[93] = -58;
        sArray[94] = 9;
        sArray[96] = -1;
        sArray[97] = 15;
        sArray[98] = -86;
        sArray[99] = 322;
        sArray[100] = -936;
        sArray[101] = 2343;
        sArray[102] = -5800;
        sArray[103] = 25249;
        sArray[104] = 15006;
        sArray[105] = -4765;
        sArray[106] = 2011;
        sArray[107] = -802;
        sArray[108] = 269;
        sArray[109] = -68;
        sArray[110] = 10;
        sArray[112] = -1;
        sArray[113] = 15;
        sArray[114] = -87;
        sArray[115] = 328;
        sArray[116] = -957;
        sArray[117] = 2394;
        sArray[118] = -5849;
        sArray[119] = 22920;
        sArray[120] = 17738;
        sArray[121] = -5298;
        sArray[122] = 2215;
        sArray[123] = -885;
        sArray[124] = 299;
        sArray[125] = -77;
        sArray[126] = 12;
        sArray[129] = 14;
        sArray[130] = -83;
        sArray[131] = 319;
        sArray[132] = -938;
        sArray[133] = 2347;
        sArray[134] = -5671;
        sArray[135] = 20396;
        sArray[136] = 20396;
        sArray[137] = -5671;
        sArray[138] = 2347;
        sArray[139] = -938;
        sArray[140] = 319;
        sArray[141] = -83;
        sArray[142] = 14;
        sArray[145] = 12;
        sArray[146] = -77;
        sArray[147] = 299;
        sArray[148] = -885;
        sArray[149] = 2215;
        sArray[150] = -5298;
        sArray[151] = 17738;
        sArray[152] = 22920;
        sArray[153] = -5849;
        sArray[154] = 2394;
        sArray[155] = -957;
        sArray[156] = 328;
        sArray[157] = -87;
        sArray[158] = 15;
        sArray[159] = -1;
        sArray[161] = 10;
        sArray[162] = -68;
        sArray[163] = 269;
        sArray[164] = -802;
        sArray[165] = 2011;
        sArray[166] = -4765;
        sArray[167] = 15006;
        sArray[168] = 25249;
        sArray[169] = -5800;
        sArray[170] = 2343;
        sArray[171] = -936;
        sArray[172] = 322;
        sArray[173] = -86;
        sArray[174] = 15;
        sArray[175] = -1;
        sArray[177] = 9;
        sArray[178] = -58;
        sArray[179] = 232;
        sArray[180] = -698;
        sArray[181] = 1749;
        sArray[182] = -4109;
        sArray[183] = 12263;
        sArray[184] = 27328;
        sArray[185] = -5498;
        sArray[186] = 2185;
        sArray[187] = -870;
        sArray[188] = 300;
        sArray[189] = -81;
        sArray[190] = 15;
        sArray[191] = -1;
        sArray[193] = 7;
        sArray[194] = -47;
        sArray[195] = 191;
        sArray[196] = -578;
        sArray[197] = 1448;
        sArray[198] = -3366;
        sArray[199] = 9568;
        sArray[200] = 29105;
        sArray[201] = -4922;
        sArray[202] = 1916;
        sArray[203] = -757;
        sArray[204] = 261;
        sArray[205] = -71;
        sArray[206] = 13;
        sArray[207] = -1;
        sArray[209] = 5;
        sArray[210] = -36;
        sArray[211] = 148;
        sArray[212] = -449;
        sArray[213] = 1121;
        sArray[214] = -2573;
        sArray[215] = 6977;
        sArray[216] = 30535;
        sArray[217] = -4056;
        sArray[218] = 1533;
        sArray[219] = -597;
        sArray[220] = 204;
        sArray[221] = -55;
        sArray[222] = 10;
        sArray[225] = 3;
        sArray[226] = -25;
        sArray[227] = 105;
        sArray[228] = -318;
        sArray[229] = 786;
        sArray[230] = -1765;
        sArray[231] = 4540;
        sArray[232] = 31584;
        sArray[233] = -2894;
        sArray[234] = 1042;
        sArray[235] = -391;
        sArray[236] = 128;
        sArray[237] = -33;
        sArray[238] = 6;
        sArray[241] = 2;
        sArray[242] = -15;
        sArray[243] = 64;
        sArray[244] = -190;
        sArray[245] = 455;
        sArray[246] = -974;
        sArray[247] = 2302;
        sArray[248] = 32224;
        sArray[249] = -1439;
        sArray[250] = 450;
        sArray[251] = -142;
        sArray[252] = 36;
        sArray[253] = -5;
        sArray[258] = -7;
        sArray[259] = 27;
        sArray[260] = -71;
        sArray[261] = 142;
        sArray[262] = -227;
        sArray[263] = 299;
        sArray[264] = 32439;
        sArray[265] = 299;
        sArray[266] = -227;
        sArray[267] = 142;
        sArray[268] = -71;
        sArray[269] = 27;
        sArray[270] = -7;
        sinc_table = sArray;
    }

    public Sample() {
        this.set_sample_data(new short[0], 0, 0, false);
    }

    public void set_sample_data(short[] data, int loop_start, int loop_length, boolean ping_pong) {
        if (loop_start < 0) {
            loop_start = 0;
        }
        if (loop_start >= data.length) {
            loop_start = data.length - 1;
        }
        if (loop_start + loop_length > data.length) {
            loop_length = data.length - loop_start;
        }
        if (loop_length <= 1) {
            this.sample_data = new short[8 + data.length + 24];
            System.arraycopy(data, 0, this.sample_data, 8, data.length);
            int offset = 0;
            while (offset < 8) {
                short sample = this.sample_data[8 + data.length - 1];
                this.sample_data[8 + data.length + offset] = sample = (short)(sample * (8 - offset) / 8);
                ++offset;
            }
            loop_start = 8 + data.length + 8;
            loop_length = 1;
        } else {
            short sample;
            int offset;
            if (ping_pong) {
                this.sample_data = new short[8 + loop_start + loop_length * 2 + 16];
                System.arraycopy(data, 0, this.sample_data, 8, loop_start + loop_length);
                offset = 0;
                while (offset < loop_length) {
                    this.sample_data[8 + loop_start + loop_length + offset] = sample = data[loop_start + loop_length - offset - 1];
                    ++offset;
                }
                loop_start += 8;
                loop_length *= 2;
            } else {
                this.sample_data = new short[8 + loop_start + loop_length + 16];
                System.arraycopy(data, 0, this.sample_data, 8, loop_start + loop_length);
                loop_start += 8;
            }
            offset = 0;
            while (offset < 16) {
                this.sample_data[loop_start + loop_length + offset] = sample = this.sample_data[loop_start + offset];
                ++offset;
            }
        }
        this.loop_start = loop_start;
        this.loop_length = loop_length;
    }

    public void resample_nearest(int sample_idx, int sample_frac, int step, int left_gain, int right_gain, int[] mix_buffer, int frame_offset, int frames) {
        sample_idx += 8;
        int loop_end = this.loop_start + this.loop_length - 1;
        int offset = frame_offset << 1;
        int end = frame_offset + frames - 1 << 1;
        while (offset <= end) {
            if (sample_idx > loop_end) {
                if (this.loop_length <= 1) break;
                sample_idx = this.loop_start + (sample_idx - this.loop_start) % this.loop_length;
            }
            int n = offset;
            mix_buffer[n] = mix_buffer[n] + (this.sample_data[sample_idx] * left_gain >> 15);
            int n2 = offset + 1;
            mix_buffer[n2] = mix_buffer[n2] + (this.sample_data[sample_idx] * right_gain >> 15);
            offset += 2;
            sample_idx += (sample_frac += step) >> 15;
            sample_frac &= Short.MAX_VALUE;
        }
    }

    public void resample_linear(int sample_idx, int sample_frac, int step, int left_gain, int right_gain, int[] mix_buffer, int frame_offset, int frames) {
        sample_idx += 8;
        int loop_end = this.loop_start + this.loop_length - 1;
        int offset = frame_offset << 1;
        int end = frame_offset + frames - 1 << 1;
        while (offset <= end) {
            if (sample_idx > loop_end) {
                if (this.loop_length <= 1) break;
                sample_idx = this.loop_start + (sample_idx - this.loop_start) % this.loop_length;
            }
            int amplitude = this.sample_data[sample_idx];
            amplitude += (this.sample_data[sample_idx + 1] - amplitude) * sample_frac >> 15;
            int n = offset;
            mix_buffer[n] = mix_buffer[n] + (amplitude * left_gain >> 15);
            int n2 = offset + 1;
            mix_buffer[n2] = mix_buffer[n2] + (amplitude * right_gain >> 15);
            offset += 2;
            sample_idx += (sample_frac += step) >> 15;
            sample_frac &= Short.MAX_VALUE;
        }
    }

    public void resample_sinc(int sample_idx, int sample_frac, int step, int left_gain, int right_gain, int[] mix_buffer, int frame_offset, int frames) {
        int loop_end = this.loop_start + this.loop_length - 1;
        int offset = frame_offset << 1;
        int end = frame_offset + frames - 1 << 1;
        while (offset <= end) {
            if (sample_idx > loop_end) {
                if (this.loop_length <= 1) break;
                sample_idx = this.loop_start + (sample_idx - this.loop_start) % this.loop_length;
            }
            int table_idx = sample_frac >> 11 << 4;
            int a1 = sinc_table[table_idx + 0] * this.sample_data[sample_idx + 0] >> 15;
            a1 += sinc_table[table_idx + 1] * this.sample_data[sample_idx + 1] >> 15;
            a1 += sinc_table[table_idx + 2] * this.sample_data[sample_idx + 2] >> 15;
            a1 += sinc_table[table_idx + 3] * this.sample_data[sample_idx + 3] >> 15;
            a1 += sinc_table[table_idx + 4] * this.sample_data[sample_idx + 4] >> 15;
            a1 += sinc_table[table_idx + 5] * this.sample_data[sample_idx + 5] >> 15;
            a1 += sinc_table[table_idx + 6] * this.sample_data[sample_idx + 6] >> 15;
            a1 += sinc_table[table_idx + 7] * this.sample_data[sample_idx + 7] >> 15;
            a1 += sinc_table[table_idx + 8] * this.sample_data[sample_idx + 8] >> 15;
            a1 += sinc_table[table_idx + 9] * this.sample_data[sample_idx + 9] >> 15;
            a1 += sinc_table[table_idx + 10] * this.sample_data[sample_idx + 10] >> 15;
            a1 += sinc_table[table_idx + 11] * this.sample_data[sample_idx + 11] >> 15;
            a1 += sinc_table[table_idx + 12] * this.sample_data[sample_idx + 12] >> 15;
            a1 += sinc_table[table_idx + 13] * this.sample_data[sample_idx + 13] >> 15;
            a1 += sinc_table[table_idx + 14] * this.sample_data[sample_idx + 14] >> 15;
            a1 += sinc_table[table_idx + 15] * this.sample_data[sample_idx + 15] >> 15;
            int a2 = sinc_table[table_idx + 16] * this.sample_data[sample_idx + 0] >> 15;
            a2 += sinc_table[table_idx + 17] * this.sample_data[sample_idx + 1] >> 15;
            a2 += sinc_table[table_idx + 18] * this.sample_data[sample_idx + 2] >> 15;
            a2 += sinc_table[table_idx + 19] * this.sample_data[sample_idx + 3] >> 15;
            a2 += sinc_table[table_idx + 20] * this.sample_data[sample_idx + 4] >> 15;
            a2 += sinc_table[table_idx + 21] * this.sample_data[sample_idx + 5] >> 15;
            a2 += sinc_table[table_idx + 22] * this.sample_data[sample_idx + 6] >> 15;
            a2 += sinc_table[table_idx + 23] * this.sample_data[sample_idx + 7] >> 15;
            a2 += sinc_table[table_idx + 24] * this.sample_data[sample_idx + 8] >> 15;
            a2 += sinc_table[table_idx + 25] * this.sample_data[sample_idx + 9] >> 15;
            a2 += sinc_table[table_idx + 26] * this.sample_data[sample_idx + 10] >> 15;
            a2 += sinc_table[table_idx + 27] * this.sample_data[sample_idx + 11] >> 15;
            a2 += sinc_table[table_idx + 28] * this.sample_data[sample_idx + 12] >> 15;
            a2 += sinc_table[table_idx + 29] * this.sample_data[sample_idx + 13] >> 15;
            a2 += sinc_table[table_idx + 30] * this.sample_data[sample_idx + 14] >> 15;
            int amplitude = a1 + (((a2 += sinc_table[table_idx + 31] * this.sample_data[sample_idx + 15] >> 15) - a1) * (sample_frac & 0x7FF) >> 11);
            int n = offset;
            mix_buffer[n] = mix_buffer[n] + (amplitude * left_gain >> 15);
            int n2 = offset + 1;
            mix_buffer[n2] = mix_buffer[n2] + (amplitude * right_gain >> 15);
            offset += 2;
            sample_idx += (sample_frac += step) >> 15;
            sample_frac &= Short.MAX_VALUE;
        }
    }

    public boolean has_finished(int sample_idx) {
        boolean finished = false;
        if (this.loop_length <= 1 && sample_idx > this.loop_start) {
            finished = true;
        }
        return finished;
    }
}
