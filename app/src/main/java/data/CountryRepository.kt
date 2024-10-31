package data

import com.example.worlddata.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * The repository for providing the countries.
 */
class CountryRepository @Inject constructor(
	private val countryDao: CountryDao
) {


	/**
	 * Get all the countries from the database
	 */
	fun getCountries(): Flow<List<Country>> {

		// todo: for testing. delete at the end.
		return flow {
			emit(listOf(
				Country("Panama", "PA", 76453, 4456),
				Country("Mexico", "MX", 4567, 23423),
				Country("Madagascar", "MG", 43566, 9986775),
				Country("Liberia", "LR", 2342, 97878),
				Country("Greece", "GR", 2342, 5556),
				Country("United States", "US", 987867, 21),
				Country("Ukraine", "UA", 45, 23),
			))
		}
//		return countryDao.getCountries()
	}


	/**
	 * Insert a list of countries into the database
	 */
	private suspend fun insertCountries(countries: List<Country>) {
		countryDao.insertCountries(countries)
	}


	/**
	 * Delete all countries from the database
	 */
	private suspend fun deleteAllCountries() {
		countryDao.deleteAll()
	}


	/**
	 * A map of country codes to their corresponding flag drawable resources.
	 */
	val countryFlagMap = mapOf(
		"AF" to R.drawable.ic_flag_af,
		"AL" to R.drawable.ic_flag_al,
		"DZ" to R.drawable.ic_flag_dz,
		"AD" to R.drawable.ic_flag_ad,
		"AO" to R.drawable.ic_flag_ao,
		"AG" to R.drawable.ic_flag_ag,
		"AR" to R.drawable.ic_flag_ar,
		"AM" to R.drawable.ic_flag_am,
		"AU" to R.drawable.ic_flag_au,
		"AT" to R.drawable.ic_flag_at,
		"AZ" to R.drawable.ic_flag_az,
		"BS" to R.drawable.ic_flag_bs,
		"BH" to R.drawable.ic_flag_bh,
		"BD" to R.drawable.ic_flag_bd,
		"BB" to R.drawable.ic_flag_bb,
		"BY" to R.drawable.ic_flag_by,
		"BE" to R.drawable.ic_flag_be,
		"BZ" to R.drawable.ic_flag_bz,
		"BJ" to R.drawable.ic_flag_bj,
		"BT" to R.drawable.ic_flag_bt,
		"BO" to R.drawable.ic_flag_bo,
		"BA" to R.drawable.ic_flag_ba,
		"BW" to R.drawable.ic_flag_bw,
		"BR" to R.drawable.ic_flag_br,
		"BN" to R.drawable.ic_flag_bn,
		"BG" to R.drawable.ic_flag_bg,
		"BF" to R.drawable.ic_flag_bf,
		"BI" to R.drawable.ic_flag_bi,
		"CV" to R.drawable.ic_flag_cv,
		"KH" to R.drawable.ic_flag_kh,
		"CM" to R.drawable.ic_flag_cm,
		"CA" to R.drawable.ic_flag_ca,
		"CF" to R.drawable.ic_flag_cf,
		"TD" to R.drawable.ic_flag_td,
		"CL" to R.drawable.ic_flag_cl,
		"CN" to R.drawable.ic_flag_cn,
		"CO" to R.drawable.ic_flag_co,
		"KM" to R.drawable.ic_flag_km,
		"CD" to R.drawable.ic_flag_cd,
		"CG" to R.drawable.ic_flag_cg,
		"CR" to R.drawable.ic_flag_cr,
		"HR" to R.drawable.ic_flag_hr,
		"CU" to R.drawable.ic_flag_cu,
		"CY" to R.drawable.ic_flag_cy,
		"CZ" to R.drawable.ic_flag_cz,
		"DK" to R.drawable.ic_flag_dk,
		"DJ" to R.drawable.ic_flag_dj,
		"DM" to R.drawable.ic_flag_dm,
		"DO" to R.drawable.ic_flag_do,
		"EC" to R.drawable.ic_flag_ec,
		"EG" to R.drawable.ic_flag_eg,
		"SV" to R.drawable.ic_flag_sv,
		"GQ" to R.drawable.ic_flag_gq,
		"ER" to R.drawable.ic_flag_er,
		"EE" to R.drawable.ic_flag_ee,
		"SZ" to R.drawable.ic_flag_sz,
		"ET" to R.drawable.ic_flag_et,
		"FJ" to R.drawable.ic_flag_fj,
		"FI" to R.drawable.ic_flag_fi,
		"FR" to R.drawable.ic_flag_fr,
		"GA" to R.drawable.ic_flag_ga,
		"GM" to R.drawable.ic_flag_gm,
		"GE" to R.drawable.ic_flag_ge,
		"DE" to R.drawable.ic_flag_de,
		"GH" to R.drawable.ic_flag_gh,
		"GR" to R.drawable.ic_flag_gr,
		"GD" to R.drawable.ic_flag_gd,
		"GT" to R.drawable.ic_flag_gt,
		"GN" to R.drawable.ic_flag_gn,
		"GW" to R.drawable.ic_flag_gw,
		"GY" to R.drawable.ic_flag_gy,
		"HT" to R.drawable.ic_flag_ht,
		"HN" to R.drawable.ic_flag_hn,
		"HU" to R.drawable.ic_flag_hu,
		"IS" to R.drawable.ic_flag_is,
		"IN" to R.drawable.ic_flag_in,
		"ID" to R.drawable.ic_flag_id,
		"IR" to R.drawable.ic_flag_ir,
		"IQ" to R.drawable.ic_flag_iq,
		"IE" to R.drawable.ic_flag_ie,
		"IL" to R.drawable.ic_flag_il,
		"IT" to R.drawable.ic_flag_it,
		"JM" to R.drawable.ic_flag_jm,
		"JP" to R.drawable.ic_flag_jp,
		"JO" to R.drawable.ic_flag_jo,
		"KZ" to R.drawable.ic_flag_kz,
		"KE" to R.drawable.ic_flag_ke,
		"KI" to R.drawable.ic_flag_ki,
		"KP" to R.drawable.ic_flag_kp,
		"KR" to R.drawable.ic_flag_kr,
		"XK" to R.drawable.ic_flag_xk,
		"KW" to R.drawable.ic_flag_kw,
		"KG" to R.drawable.ic_flag_kg,
		"LA" to R.drawable.ic_flag_la,
		"LV" to R.drawable.ic_flag_lv,
		"LB" to R.drawable.ic_flag_lb,
		"LS" to R.drawable.ic_flag_ls,
		"LR" to R.drawable.ic_flag_lr,
		"LY" to R.drawable.ic_flag_ly,
		"LI" to R.drawable.ic_flag_li,
		"LT" to R.drawable.ic_flag_lt,
		"LU" to R.drawable.ic_flag_lu,
		"MG" to R.drawable.ic_flag_mg,
		"MW" to R.drawable.ic_flag_mw,
		"MY" to R.drawable.ic_flag_my,
		"MV" to R.drawable.ic_flag_mv,
		"ML" to R.drawable.ic_flag_ml,
		"MT" to R.drawable.ic_flag_mt,
		"MH" to R.drawable.ic_flag_mh,
		"MR" to R.drawable.ic_flag_mr,
		"MU" to R.drawable.ic_flag_mu,
		"MX" to R.drawable.ic_flag_mx,
		"FM" to R.drawable.ic_flag_fm,
		"MD" to R.drawable.ic_flag_md,
		"MC" to R.drawable.ic_flag_mc,
		"MN" to R.drawable.ic_flag_mn,
		"ME" to R.drawable.ic_flag_me,
		"MA" to R.drawable.ic_flag_ma,
		"MZ" to R.drawable.ic_flag_mz,
		"MM" to R.drawable.ic_flag_mm,
		"NA" to R.drawable.ic_flag_na,
		"NR" to R.drawable.ic_flag_nr,
		"NP" to R.drawable.ic_flag_np,
		"NL" to R.drawable.ic_flag_nl,
		"NZ" to R.drawable.ic_flag_nz,
		"NI" to R.drawable.ic_flag_ni,
		"NE" to R.drawable.ic_flag_ne,
		"NG" to R.drawable.ic_flag_ng,
		"MK" to R.drawable.ic_flag_mk,
		"NO" to R.drawable.ic_flag_no,
		"OM" to R.drawable.ic_flag_om,
		"PK" to R.drawable.ic_flag_pk,
		"PW" to R.drawable.ic_flag_pw,
		"PA" to R.drawable.ic_flag_pa,
		"PG" to R.drawable.ic_flag_pg,
		"PY" to R.drawable.ic_flag_py,
		"PE" to R.drawable.ic_flag_pe,
		"PH" to R.drawable.ic_flag_ph,
		"PL" to R.drawable.ic_flag_pl,
		"PT" to R.drawable.ic_flag_pt,
		"QA" to R.drawable.ic_flag_qa,
		"RO" to R.drawable.ic_flag_ro,
		"RU" to R.drawable.ic_flag_ru,
		"RW" to R.drawable.ic_flag_rw,
		"KN" to R.drawable.ic_flag_kn,
		"LC" to R.drawable.ic_flag_lc,
		"VC" to R.drawable.ic_flag_vc,
		"WS" to R.drawable.ic_flag_ws,
		"SM" to R.drawable.ic_flag_sm,
		"SA" to R.drawable.ic_flag_sa,
		"SN" to R.drawable.ic_flag_sn,
		"RS" to R.drawable.ic_flag_rs,
		"SC" to R.drawable.ic_flag_sc,
		"SL" to R.drawable.ic_flag_sl,
		"SG" to R.drawable.ic_flag_sg,
		"SK" to R.drawable.ic_flag_sk,
		"SI" to R.drawable.ic_flag_si,
		"SB" to R.drawable.ic_flag_sb,
		"SO" to R.drawable.ic_flag_so,
		"ZA" to R.drawable.ic_flag_za,
		"SS" to R.drawable.ic_flag_ss,
		"ES" to R.drawable.ic_flag_es,
		"LK" to R.drawable.ic_flag_lk,
		"SD" to R.drawable.ic_flag_sd,
		"SR" to R.drawable.ic_flag_sr,
		"SE" to R.drawable.ic_flag_se,
		"CH" to R.drawable.ic_flag_ch,
		"SY" to R.drawable.ic_flag_sy,
		"TW" to R.drawable.ic_flag_tw,
		"TJ" to R.drawable.ic_flag_tj,
		"TZ" to R.drawable.ic_flag_tz,
		"TH" to R.drawable.ic_flag_th,
		"TG" to R.drawable.ic_flag_tg,
		"TO" to R.drawable.ic_flag_to,
		"TT" to R.drawable.ic_flag_tt,
		"TN" to R.drawable.ic_flag_tn,
		"TR" to R.drawable.ic_flag_tr,
		"TM" to R.drawable.ic_flag_tm,
		"TV" to R.drawable.ic_flag_tv,
		"UG" to R.drawable.ic_flag_ug,
		"UA" to R.drawable.ic_flag_ua,
		"AE" to R.drawable.ic_flag_ae,
		"GB" to R.drawable.ic_flag_gb,
		"US" to R.drawable.ic_flag_us,
		"UY" to R.drawable.ic_flag_uy,
		"UZ" to R.drawable.ic_flag_uz,
		"VU" to R.drawable.ic_flag_vu,
		"VE" to R.drawable.ic_flag_ve,
		"VN" to R.drawable.ic_flag_vn,
		"YE" to R.drawable.ic_flag_ye,
		"ZM" to R.drawable.ic_flag_zm,
		"ZW" to R.drawable.ic_flag_zw
	)

}