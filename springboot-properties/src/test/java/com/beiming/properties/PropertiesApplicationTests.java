package com.beiming.properties;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootTest(classes = PropertiesApplication.class)
class PropertiesApplicationTests {

    private static String aa = "dc1QESXG9PI-GYxdXcyDlCca9pX0geYWpGoIkbqpJWVBmogIMGD3NofZseBZVAG_EgJHOpojgAIUjyne3R2OAueA\n" +
            "dcJYkUXZD9f36PCisK1wwNUW50m5_Dd_nnGxNa51baFhWxnMx6cPKdXNoiXQz0yF9YmvmFvtSNkFx1qvCg0Cst-w\n" +
            "dceTj8UsZ5PwYysziaXn5broZN0HdpmZFA114Y_hHNS6LmzKYlA5GS9zabLe92fHlkFOYH4JDNVTZ5ETYoKmz0lQ\n" +
            "dc1OxOVc9FLlx1YYhB7WkC4dQHyxFmJfhQ5v3_gS5-7FYHcxX5Kf5EuIoO-UY24zyu97GoKfhNRV_IIJCc4bZaEw\n" +
            "dc9ksKO9gSDdAy2wFtaVFBtr1AqW4cgilF0n4zuIyXPjrRkHq5jqR8lbNR5WR6LP9DC_WYVBKB3FyP4MvzpK9lzw\n" +
            "dcEY3T84cYAMB8FX7SoK4HufevUo1PcOWog1Of1hQRmYVzQs0xlrIUGo8Wiu8CyeUcjZXSMWCAmnIraLBRFCGh2g\n" +
            "dclQpUlRmQPzRXQKQCdeXhU6AxxY6OionV3fzzPv48IfAjJdhMwyxAB48lnuMihOjwOVKQBNOAld9sQT4dLeQa1Q\n" +
            "dcebqMFtNARxzFMT-U6Uw1DzpnibeATHgbIty8dp4gnccjrLSv3R0FfR4cEc-gl5PmsNnPOCT-2ljNEcCu9u5hcg\n" +
            "dcHfB2zrkiiW0Kl6R1WzJCCzzDm6M_ZSmmtB4NIq2yWPrNSMXXu7ycUQS3dGxg2VQH2kVZ17_xeLKz-Ru3Lh887Q\n" +
            "dcCyJdnXMHgvhaCeJSZ-zYQoM4xIPgeIZj4CoJLpIXLGNorY1k46qWn-ZL8gk3JkKrO5FR6p_scSwgbQeBFuEXOw\n" +
            "dcwv0Dutvvhvm4SrFEQEbFYwsrPUDeXljfHqhuZxaLHnuGIRyag5M-g-9EGpVxZgCuW9dma0CKetFELFFvdRhxRg\n" +
            "dct4X8BWpCm2bIf0L-2nWpUBgjMyBgUcSc9YEwibwH0qT-a_KqkjIDnk4E7sqvGMsGUssXnkHGIxaRdB2Dz-WHqQ\n" +
            "dcAuaS4gzl7FZvgGmtziXIw9K0PAVncQryJU4TfKQJXcJHHHVD0DNJyqrrNDNCxVkgVrTgHrtphTX_E4aNALJRtg\n" +
            "dcQ_O5DRKkW4xDPrG2LcRNNOFwD3JB-FmMxIvjTSvTd95iYhtkZ1ZGOqdb12RwNOz20hoVMAu60IUuhE2qcwsjDg\n" +
            "dcy5U0v8yU2An-tLceK2_aQBSKMaFvI45l0pv6Z5uK7d_5pb_dMENz6mmvfkb6QNF1GZzxP0tDDnCaiCFdKvreTg\n" +
            "dcZj-itewe5JsyHS77RSL_Hxfwd4iVW_dj3Uqm9sS9xKqT6R_9mBjrdskwqlHzpC5PpwaZEkiGYqejvUdi6BCigQ\n" +
            "dcXZLcZpH3cpt2K7GLKaQzuxe9mLSIufZyhhXaP9gkw3Bt2FizEDiRVj_NV2zTLOzGAaC0QqxzmrXWF4CJLUrN6A\n" +
            "dcs4uNMHd2nBz4i86Xd2XneL8AWnt_0pSITaQjeo1fukxBRpAnWfWN0Gxdl5k5ZBU_u50ePxZgvIXVrBiJ5zhcBA\n" +
            "dcGX3bW5EGKXuK3zDf7jrVNmirD8tfbYGH_h_vrdKq1qyiRm7IABGxnmAMa5uuwgQGU4dVXxX-t0mIOo4MM9qJ_A\n" +
            "dcQ_kflhSpi8Y_gSe6Sx_HseZcDB9_L8i1FR9smbB8VsPFsczkgnQnsbFipc4XPzFpTmvl_5xuatFI7Z9Ey4rpKw\n" +
            "dcoEOi7CqFCMO9lf5COeDd7AFRJUzom-s9Oou-RyORZfwguB8DyeAJ2oFWqTjacCIuJH-5PsSpdy6QyMd0pLN9-Q\n" +
            "dcpJvSffr2DmgkqT3d_VSTMam49tYdJmHnpSassoIPFWdoPC1QuTqwZB7zbHx2D_OJ3iQXFgcQVcUDETPsF0SDCw\n" +
            "dcNTJXIMcrQnj7AIXnzhNtsaiFCqbXwEVTfzqksqS1-Vo9HlG8opp5yZivAEQyBSQzUNb9YypxslCCyZJYHdCgPw\n" +
            "dcxND9j_VdbSXVFswDtJkhWuQku_cGIbFgpNZ3tk8p1xOu2gm_-QJtuBUQ_g5BjcWEPNES-cjhADO7SE9Mivd5zw\n" +
            "dcJaAi3QmKP659-PsQ3dS0sCvljOjXzRLxFYUBBmvhbz6yWqw8Gf-QzvmvVEDW7d7AXPGcXpWuicsZ2SlL9pL2qA\n" +
            "dcLszUZNfyLzJ2VNX1FlOtZF3nOyd8b-MyIl5Vgjxvmlqjh-dAmhdSokUdH5ipy4tNWmxwsFN6ATtjy9R794wpNg\n" +
            "dcYyE2YzVNfOl2kV68QVvUhHF-RcR7sj-blsdaIjGe0jPCGUcVNVZ0VJgHBIPzZC25kLnm3_-xW9i4u9ZQOKiz3A\n" +
            "dcTFEKsueY3X7JaJ2Zz8fkCS-JnGjzbjjne7_lkWdkN3psCs4i6F_5-pCGIkOTjbRSd93tFEwB9FiiKmGbFaqY-w\n" +
            "dcGZOP23UqJjdXk6Lj0mdv6J3QmJ6ahZeFIXAEjcSIG4KCnjkKDgXh43t3tMOH0s6G8kqK0NVqWFtVuIAMU9-Gaw\n" +
            "dcuSmkSfYaXWji3Lv25ImwKXXCQenbhdqEB7X3TY7CE7CNZzZDlEGbK8mYZcCRouo4I5enS4YjR3ZXtlOmVs-Gnw\n" +
            "dcjrPe7_LzPVJYLp94sAd2790icF3a9cZXUSb4ziX78gXlqLhSB33-obX1Hd2ktYlWjVFjw9Nvixrg_Q1LwBcGPA\n" +
            "dcLqXBsWqlPnuwuxpEDCDaxrFBA8FhL25MuK6BQ4zN1SxqldEKi84Gt7dLyWz0vfynI3A2kzdanV9HFVXDBXlipQ\n" +
            "dcBXkSsHTYER_QDCIDs78qz27XyF55BiGreWhH54CqgKWhlb_A87q99BXxIsjIcaF8S7FVWXYnFdepVRIsZW1bcQ\n" +
            "dcDa_6MhZgYPBaK4hBhl7O4h2rdBUwZhe4yOX4uwjQNQHrzeP8Wu8VdX6NO31DVxCSEK7PX4EAFugxNVx0TqfHaQ\n" +
            "dc-iFHF8ViQUQatzvEba9UQlUIANSOf389d9k_4Tis31jrD4-b5FSJdSPxZyB3JIKSZNUPhwGzjWnLrVdQQORdZw\n" +
            "dcNq5pQ7wl_wT0YD-XsXTpxm4_-0meIXgRTXtSAEOlyrMQ3AoIsbz6MbZ8tjNOxmznfns6jFeXKJueM4w_7fkFjg\n" +
            "dc65ykAk593m-R11g2ooU6y4jC1VEOZgHPds4ZFIjcDeJmHKToDJVhrAaW93fDw1-4et9bbwHOLFf2ykUJuyu0dQ\n" +
            "dcNZiCO5dirEg0OvDqozaxJMrYI9YzTlurI1Q7Zs0SMeeqhQ4VkXTUPoBU_eyaMBSlSEhJEXNcfcl9hLLGTl4sVA\n" +
            "dccHADOSZVlQ8K4fG1HvxvnRG0abJ48RtUoYuE6b1aP8kVZPINARK_hHdNr4S3yODWtcWo7AXOJfuETjbtUN0GNQ\n" +
            "dcYhTUCQpIynjOWHY6dXF4B8Okeym6qL2ePSKeOaBjouPWb9p45llN-ZRNS3val3fr-f1lWlJXvxAWmHWQBmoBmA\n" +
            "dc9DHHB7osGAj_gAqQVjWkn8hqNKLO-RFA4F0nL532lNNi1ATlHliQWAQtG58dN2z6CmRANcThtTdLvkDKbyz5Eg\n" +
            "dcC-c7pR9jYX3Xjui5E22yp_bU4ZGFmJxKImqKlzRTRQ_hfL-rG31mwe3_PQvCXNSifmzsBqzicjGzc-8snzpqxg\n" +
            "dckik-dNyrH06JggmpF_hwB0ElG8lqSDOQ8PjOwtmaKH48kTETHhwsGqJavLwgxelaCQ7UGtB8h_5TrTQHpoNODQ\n" +
            "dc9FWUWTfezO2DdDMdvlkazAf9v7z7LP2VaaP5J7YkfN_pz7983sP8xkeIulmRtv_Ajf7_18MP2cgCMX6a4h8NXw\n" +
            "dc9uK-rfW6H7LlAqyHMDoFVpfM8jgh0pO52mGl3UxcOJzICIC0YDgi2coZVnyGaFs3dRdNsD9Uk_vTIaPDMB2wgA\n" +
            "dcouA55WUfGeHkmKH4_9v1Gytd1dQrFlLQp78Z_TVKDhcSeqpjj8OTtHa49HqTwuP_dXZ_aLcyXRgm-0n5jpMjqQ\n" +
            "dcSVYotK0G3POmRx8MMf8l6M2Ajoo-siCOvsC1a3jkqdPw-tDR-0ysTvyu12snKpxMUUv9UmBapK1NRW5_kNVJqQ\n" +
            "dcNhclC0SJ4TKALhfq_036PTvn4RenUPi7RmI6gvNNbVmT4TM9JqpMyWyEPsdNPW5_NeWxUcQU0FpSnTqsx9LjiA\n" +
            "dcORP9bIrM-vGDxfUihRMmtWnrYH1Ht1vxKU9tD5xOMgajFleUiKMZMSiIeh2p8fST5Dc-vU5orZ75zRFejajmBg\n" +
            "dcEO2qf7ZrDu-NSf0xW7Q2q29LLwp_TZ21n3ZylumjxuINS2W75w9zHWY5BkCggQWIKJtXVsZ4yK4YsjVnz5WOQw\n" +
            "dcy8jXenMJ4C4xFgCr0M6Bm_RVgAA_sOrP-UX1styv_X9dtvr31AvRFz1wqsxkGTSUtgkVZq1KjA9W9BJLon7vjg\n" +
            "dcCG4NIWz4BMxZyJFX64vqFQXio38REL9M9v89DjWKF_S3tAHx4uPGqI97miDveEupd1SBCAQfS-iX-JeUt6qifQ\n" +
            "dcbtVQzMFV2MYsUSxIYqAErftnV_kfwEubd45jUzCfLARdGJkNgLcUjKOdcANw-X3qhQlzt_fvBQeTjvfmmrJBeg\n" +
            "dcMcnS0yuvqCeUklbmz4vE58Ip4skCJmWNuhdhLJb_KNhLidUhhU5VDDvmn59IdW2IgLOOYrlS0Mr01nnpCxebDw\n" +
            "dcv34_Wn8x2y9aPYoP_RRJxSQnW2hTMLtPjmIiCCWIsVrIlKbm9jF3bzkZRq6l0itSfAplX1O1UTmucTxE3zhseA\n" +
            "dcYbr5_5bx0eMDThZ8Ntk3fSRcNSTKhM4ZIdnt8-CGCgFJWJwXFdq5fJacLsG3aS48r-6ZRSfV_KUiCJxM6eB19A\n" +
            "dcBAOHGCTiK-W2-iEFKl-Wj5wzL4Qro4GZVJXC2E2DevZy3OKkgjcLyaJnaloN-ddiTCMaKEpoJmq1E0s5_SwGeA\n" +
            "dcRUt4of6eQGrG2Cb0pYTouP50QN_78IUFUNgQe7ktMwuCjoFnQmOHZkBqxheQXNfqGjH7vzEYnaj7XAC7gmhOcQ\n" +
            "dcGabnHdQ6eZQTqUC1FnoC38qVz5iEuBWmriJvfQhEVcnygbFaasdHWhHe5vQy2OHs7h-YwWrxwvqnYEVICLhbQQ\n" +
            "dcChzNvY_KK9fEv2X_R6QS9QbIras_NqcKRBnj_s9e5N7Hltk3ZRhYd8Zl9Sdn2dih6Xl7tXqnkSC4hlJlWNRvQw\n" +
            "dcjBTOMKB2grWtTUJtBMv28gwd5g5XTRr06Ur95-IMF5ieYG76Wsdr1AAFYLrAY_ncKHOTmLD2JupkZS54vMdn2Q\n" +
            "dcL4vVxJuIXoudYMGivunoc5o2B2va8D1HVACDmJPDW7jaA5hwjcM8g7CVposzoJhJCgmgegEDXi-wRP3iyJbxgw\n" +
            "dcZdtS9rQQmAIscDZpvUx_Z-lZpqdPOvOVpaACeNx4sYlUrbs0p6bdy4ZZphZXuxAm7ZVvjMQena6Ue-vALq_pbw\n" +
            "dcbBZFUnDlC00_JGN59cjkflxseoSNAr0d601-HlDrosZIgqW3N-usQzo9z5UaWdYzDe8IhpoQIoC7Evtr6mc2cQ\n" +
            "dcEVc9gxFlqre17pF1gMWfsnR1-W4sDH3smio4N3ns6ngE7Tag5nOl9BtZtyXgDLL6_rsLKDhJORLWoDZvrwqLCA\n" +
            "dcv4nrmKSB_hpOMnCGrlqJgW8wOzx4GqyEYi3VW6JnGNm2lwKjmw8WLVNn4PMn8uhxkoQkNKV6PkJ2KtWoCJIquw\n" +
            "dcW1yfZOOCRbb8NeA7HGpP5a1xuH4hzkclmX4sYkpQfwIkOCuVfVKa8Z7kNXJqlpMGZYUMXDLCoWU0aKLTLkMxYg\n" +
            "dcsLtyhVJiZL3GmuLO3hbJhhLvGWMCW7TiFo614oxbwam7NSf-6W8PP_J_S04w94aluq2PeHBSrX9vD3HZfxd3gQ\n" +
            "dcHLrX3MkpPCukZnJhFpRhSfeYyrR30216F2blyGPMoOnwYbSRS1LDuYhlqV4Mr2BnDF2pUq7zfQwaaZLJkWjWbA\n" +
            "dczyJNKPuUPpi3xyo8rXM6QMuD1Jxn0ovjWUiAKSp959afW_zEKigX552TeZ9RJU_flD0grxQNku1elmCuTYbNbQ\n" +
            "dciK0Ddk6Mu_szkhqZws3UhpistujBwb5SuXZ_QLXXkoSBqZkFkHUMV1vcrn8tA7bJWuoDwZBV1WIwCZwgywltDg\n" +
            "dcVkX7V36swBVhA8xVSfb4br1lO4iJZZ8ohHcxLohPbb44VkuE98hnpOuTMbKz39cJ13kvhlrJPccfzWBUiLeIFA\n" +
            "dc-2AMrZh10uDldBlIxqVN-6asLpOxMmgD7eYWAWvhG8sGWw3UQAFojl9-TZX-i_NGqp_tdGYeLvxXO1A9Fbo2RQ\n" +
            "dcjfo8eKT5yh7JAgJId3t82Y9UehcBaHwAN8ovkbxcp2QfGuzBwPEhv0fXf1XK61jInbjSZtdIcheRa5ts6x6UCQ\n" +
            "dcF3VqUM3xJvyOR-AVNx31JJhoQsfwuoGoTO_Ik3ARzCAnyE0ooNTtwIpoORMB5E8kEVXGH7igwuR4Eof-b3b0zw\n" +
            "dcI31Mzlm5wMfeWdwpTKMw9idBaBB0DTmeDyHUs6C-OJxwZfBMGwuG_GyQy-0xQg0L6GJ3jrxkU--cRtRYKlq_LA\n" +
            "dcsuQzODRzQmi61rT0dL-jn9Zvr9dV7RMOmTMbudeWZMz-rF8Xbs3t_fwsBOD5TlczXwXGLDcZkZsSySYV4Tfbcg\n" +
            "dcT9mvFvgCkgHvmt4pVH-MGPZSFluh-v8sFAesCLzOzVAQsyk-NKHHhnyzel2ON2HwMhtFXDFpocV0Z4VaFuMDMA\n" +
            "dc22IiMXpz6hokH2xP6hrCnBbdBlowijc3-r-FOqfbJPo1HP7tYuHuloYVcg2Tc6IWEojlUhmfnqxLpZCBJFEPtA\n" +
            "dcugx-ExbNs9sKSBAWAK_M7o2shOsbyUqcePAXthXQYx7veyeCINeNhMlkxsr2gRTsBK2XaEi6X7xxX-ybmvMEfg\n" +
            "dcP3BKioJArN6_J_KrB130UKMMU5qooCOmk3CoJdrwiC46mTMVVOQzbbiYxzTHINb9NTpogl4S0jO28dlAHsaxRw\n" +
            "dccENK7V_fv-q-yM7PPzmjVC3IjLrADwjgSyXZG3MhMXh_m9C64SEek448ab5NAYXcT3z0Spn31OOUMLQg42SYBQ\n" +
            "dcRHvPUoxA1tewkBkxt1MrvDKcTSkd2fxWsnfGO9dXu7jl2G03UAnRMO2nTQnKY2g0MJDCszR_7g0Y91ySRRV5OQ\n" +
            "dchYQoBQOjZdrvP4Z8bgMFyImatjTCjMnQSDwGMkQ5DKold3IN59gSJzDZXjvmU7zVUGuNkaA3iU_8N-RaXTqp9w\n" +
            "dcUuSDNhAGPXNHOrm5Ier2KQByZAC2GPmtojvHNh8wX-9scPCJ69PH_YrQRZGkIPiNuBtp5NmAyY_StW7k531h3g\n" +
            "dc2jsIl08nWkEzXJUey2RnoeHGvCF6i5_31EKbrRBQFg7A42UuR3Y7eABHjmlIn5suBM9KCGHOZEwpz55loDCtOg\n" +
            "dcv4Q-b4f_X2APNIW3HZWRmJMzZuP8Ufwsa_YgWXnP5Ly-cWo1gOzR0H7ef4M6iKuuuy7zIsJKdZKgvAY208GVvA\n" +
            "dcbCFegXZVMhXeXzNLk30a5StEIGYudl6KtB95dmuYOyQ03idniJShPjLNU0SvjIIDcSeUJQQNHWFAf_igPkaGvQ\n" +
            "dc1SKtxAgKOeBY2hyA3nhj5uTQ6HvNsbs2ARohnqoKbCSCXIPT9-b2FKZVO1mdHMH3FnBMqh3lUDgcrMl-Xnggng\n" +
            "dcIpzI945O3OWGr3RZzO9cp4oj4eJj_kfsH09cWwyCEU-LYmhqX0K0SFmEMac9vvTKLDNQSXRNd8w-nT6uPkptDw\n" +
            "dc2T3WhALCB4Q2N-mBcKQhjS_w-KkJalCm8Qj1VhkAvJfem26xFToEtGkybiA0cu7o3FfSKm-mJEaFSFAW7snFmg\n" +
            "dcP4QRmRpaSAdnCZNGzK3uzs8PGxIlLT96d3xufwKczi54rLniZ0l-Z9WWsGwGxx5b6Bi1rciXM2bWjZRQ3xKERg\n" +
            "dcTLkMDI4opIp5_PJlLSBiD8mADfxOAIGlvgSSFG20QUcTUDwnWNeTo1Ul5j5N4fxwyj0QbuhY2BZRBsYoimFMjA\n" +
            "dcUWgNTeWFFXi4MdRgD1h55YN3kTQdu6YSSZTTB8KBt7dfwWGYatk8W_GgwXxJGadF5bY5GcztSKdJttgStSHI2A\n" +
            "dcrZxxed35m9RDiGnQpr_F2xRgVVQk8X5_jKb4tc9STLtNevhf1Nw9LPVPy095AcskWlXehjABOVqJ_296rbiAIA\n" +
            "dcnXiib4PzZ1T7wmIZtUmEOtfZPtRdOwXRLownUBUA_000pq4rU46p5QpgW-Z9iLJGQ7KduxQXHH2j61PBxYOntQ\n" +
            "dc4Svp3SZqPblfEINEzyn_83seOZvtgl3ruVHTdEtnBSN9RFq03GhK4aPu95MMa4D3vmZMiixMQJgrTMEgpTEPEg\n" +
            "dc-NB2fmbQwC6iw4Yv4aAew2wLRJzAR5nPCutiQ-Rj6b1KIyzLg2u01HoOUCu1s0fxD_3M_g41uaDvPoQVmmUT5A\n" +
            "dcA_VeWxp-OOg8cvBin42LxZ0CkxZVtI58wACN_1AmEU5qyk-KhKgIUzCDTE9dy5sO7e5sqDfcoStK34A_ePtoCA\n" +
            "dcrN3PzCeO6D6FXTVM0HqnQbNeZogu-9wrNzeyfAAE3ASNSDSii263_2GWAB4VHbsdaezMDmAog0kU7BNISixHcw\n" +
            "dc8IwggAMGRR0nGvXY8Iu55UaWWtI0RCD-IiiaR6TPVNh1aFi-9fcEy6A9dJg8d4AwLqQ8jzbXOD2AyBiYTOkdTw\n" +
            "dcpCoT2jE5pS89XF4gt-42UvmIZrtlup7P04E9Q0F3-iQ3x64cAnX5S1z1974laACqeLbzBwXiyFX-ueHFCYU1QQ\n" +
            "dcQhls6J-admfT129-EHtBpdYZuaSp1Qg1MQpEwQwwVNnFpABP_AlogXKMnk4Ut8694e6EyAGZIsiVD9R4gQn0Uw\n" +
            "dcyltvqDIcedj6frzVoZ1OlzIdOeVCIwPZJD9JHqbJMz4ZjdiZSVT_O3u2Ors5kSoXgqFWV7UoWsvquqUp4y3kow\n" +
            "dcKFldTPGLvHYp782rdSfsoC6E1Cjr2yo4P4iCCQGK7u3_aIKBcTnMwmr7lVYaB5PhegflH4664j2hq0UxNnJyIQ\n" +
            "dcPzrkmr5pwvNu83F-7RUa9OFNKFDyC8lumJEtrFQt7HVNbkoi7g3bdJzGoyCm_aHNSaxh_O2rl2m6CFWk0i41Rg\n" +
            "dcyfsK8pBg-9mGxhy1RAtK6x9feunw-hJ2jGXtyt43QoH8ghVc9PmPtMXL5JqrNl2nDNFjC8VjzhzVPcqoKrCAWA\n" +
            "dcrCiJGl0KfwiX9IQLxj6pt7g3IePCPYbRYYtjIc-NO4mwq1xF6tircME195kkec5Y_Cbc0SPF4O1ypMsYHY3w6A\n" +
            "dcANqJphRE26j-0-sUcmmFo9Nw6r2ZSeJlNiQzArctREJfd6vaStyMZAQakgpCPSFvjZPvjc51duxJ-tYQ67KXUA\n" +
            "dcOflzSn70UHSk4q5hz-giwaPw5WuI_uVNK_rm5uq054u9w-k6zarMje8MtAqqJ7VtheSgF5aH8ZGvUScHSBuTiA\n" +
            "dc0VoXRqf3COr8V47y1MuAnXHhOB7ODl7zuLOqPwFRWo0gCbstegOTLw9F0EpMYF-V9qKlsud7RJJxM9jQ_qEUeQ\n" +
            "dcFkBe_jlFo0bWaHK2EQZAJiCBAYqmiuAlMHr7iXH2bg-9uksbkiRu0eN7aW0svta8ULSs8np1yvZkgp0JBSiM-A\n" +
            "dcu2S1Dt2bWABmiLVxaon9m7e_akE5S6zEKxJbgOVTdPK-TCG9K36VcTWoZpSo8eEV7B7cVuBe27wy9cQxHM2nVQ\n" +
            "dc2mWwHxzFpBMSMoE7J0DFu7pppDpZ6lfv1ScSAEoTPQ3tyGX99ngq_IF5k2ABxLgalryb6T_2DOJpsaaDOTQQ6w\n" +
            "dcBhv0dV989tTV9zIdrdqRYhOqyiohGrR2cpFmZRFB_iTL_aHFhZyHp1xqkQaAHETaQiy17hFXOUZ8-SVJmv6x0w\n" +
            "dcS3rz86-U5qjK0hfdfToRAyXpTkwpGFgfV9PM-WMF7a90BrJNW4sjNbPGwzlbFmaZi3Dz4ofaO-chi9Te1BpMrA\n" +
            "dcKCEHfKs742E-Ein2MUIJCx-OLWxFysACTORJmqWfxEexY1QPm6h6X5KrpaMzaEfcUtnPhaPt-9nG2udwnvvNpQ\n" +
            "dcZcyqgmN27Xs-mVxzLOSsNIucX-0XtPvIXg6bcXlJmUCJuk5ugw1zrJFjxselvsstesUVev_HtIRdW5tFIM_jQg\n" +
            "dcdKzPKl_NLOE9lYXgGPHkL_KWDodzOx6lBd_QKerfmbonu8PXLYX537zwC6bI5IYi0aGO9VRXLmvX2075ploucg\n" +
            "dc953jkMvKEdum--CsXh58TI2I-6VjpjiEDkhrF775N5w06JYNuK9DkZdYwiJiOB9ldeU2sdWNAMxsIdfjwFE7rw\n" +
            "dch-7sq7N5Z8QNHfRJTeHVvfI5gfxQ0QNh7iQANZArtUwkPzgSiZWJt8zMjxNNIJ8soSancUUDImc2iXuzYT2png\n" +
            "dcxuSEa-fYPeqZobCAqU09pqqssho2zBi_5uOuqQ2nfMkrYjvBPjgK759VTKQZlYTxIdZh_ObhkWQMw2J3-4ijbw\n" +
            "dcrgymgxGCiA4HEE1rt18TTcCMTeUq-H9-ljHaLH_oFUlwKb0BWUwPKHAwn9u-ChRJ9GVIdouuggfB08tYhORkzQ\n" +
            "dc_AXO_QDXv9dQwQ-uOaRMLA1VnZWHnniGrGPx3ZMUWJmbOZxNgTz7TDCzUfV9FUsSPUoFtU2BIbkSKiyEyU5vsw\n" +
            "dcG416HAenPCXuQyU8fPCBncfosC5queP78nd1Do-fIhVyWlfmCK4F5ggzPJUUpgcY4ktf2m9YSuznk3MF0a2fMg\n" +
            "dcIR55jARfthPNHjEqWAfHfqSpbHewM52WIuTA1lEif0VIaYBqefuf_oqfoQM6XpB18tXT2RbUK_3cXHNbxpLRhA\n" +
            "dcRrM_1LACFqlQRQRZqlqW73A1Muipa7sfW6oGCuDnG4PqxRA-mlaaL3Mnoay4s81gLU7VWuqn9gNFYbQzUo1x_g\n" +
            "dcRsOSWlWXLyttbPJ_qG0i2F33QQSxvg4f4Z5vKrFh4zwFsJW2iOCocQ2jTUOUcpgQvyDpF8gKg_qIIj7sPUvjVA\n" +
            "dcuVBoV2SSfGGdTElhGhuz6IpGoXdpwBnxpxLbB4B6WBsp_GVTk-DEpjP1-CYO0JV-UVxxP9oC5Y1VAkJus-TYGQ\n" +
            "dcLisuJuKuTXqgrWEPHpDdS1ntOjJrL8rx0-E5rYrWLNoxscsgcijfsbxbX-O3dLvrJuPEYrfeonLY1phpHKGUuw\n" +
            "dcFKRHo3OGCzQHhc5Pag6F1C018o3qs0CWXDyYqVh2XaDOzUvqr2-9sVd4y3x59Co63q7aAJFTDiee5XAceU7HfQ\n" +
            "dc7VWWu4ydvd2r4ukCRsSn4MAOMrtsrAzPbY14NKPEd5LsEI_40bGzA44D0Pt-DEES3SsxrzkgMLlwZsv6N6kEqQ\n" +
            "dczKCg1dyAYBFLtDKJSXWE0ngDqlGVS7ag3LtITzG7FokdI1UPfz5NCSl0Ij_EXsEkFdUeAidStaIF_nGCruYl7A\n" +
            "dc9VLNGwj1BV06d3J2JjJeaLwqo62fLHAx_MpIcpi3TbcxKJrxgpns1SbsKR9zajxfhB2CXzQx8WUpXcogWG-Bhw\n" +
            "dcNip6A_DXcQTNFgt0D5MxdBe5A0ORPvP18AyUdUIsabL5zFp_pcWfmkGPzzjwCKJgRW37EoLPFXI_7ABuunh_fA\n" +
            "dcTL9YFxkv3YdTsb3oQsYOAERQwpwFJcv2xYLgHSbSMEMItjjkii1ulbvkwN-z8K7j9A8Mdmfp080i1WBqTglpdA\n" +
            "dc7oEWRhhW9pk4WNRmIGjTKsXzrRINliLxbt0HcUpKu2zPndwITeJRH3WwO63ILmfIVjvco65peG3G1gTunn2RZA\n" +
            "dcz-Eazfm-0t0X4gdCloL_wsBUD9kqTRQfd750L6BJ_9ba2TfnCCxGjLjeaozfHCDQz-2as0PdVDugm_rqi01Gpg\n" +
            "dcwlksaqRVmnp53l8lpjK3yy603LKaSEMSrQh4rWZAB8N7Cn_WBj4jF3W8mDTf5vfJGt3NQEmSqXh4l5lodhNkcQ\n" +
            "dcYYRVC-iYFiiR7XX3nf-dAv49HSsmsAJUjL95O805Oef7eVySOUruc1F9yBZDbeW3W4PsRxA-3ou36DC_-jxGxA\n" +
            "dcPgrq0PgvWyQfzJlThOxV6ml-Rpisnj8nFns03xv7HhDzeBM6LknOT1lNZ_S89KYvTEyeiXInlPtNeVtMXJ2eJw\n" +
            "dc-jE1FyodhLb3UDHsA_hC3-C6ojcSQwEIP1HmpO_kBFme4jQytKV_yJb8MEi8AKfASD9QQcFoNoHNWbmOaMDNog\n" +
            "dcRbq-YAV_J1e-Oy5cD8Qr6UQtlp-YwaAGkLkQFTJd_UqynaaYnUdcSmI0jTYzNtNjZtMbJADMY-6MXoajZ_Bc4w\n" +
            "dcvddY6ikS-Ivx_0DF_SfYHgft_TyE9vK4hnI-i_kYLhtVrYelqBDtcVBjXS19Vtp3VZ1KhrtQeB5KmEbg1G6TEw\n" +
            "dc4RS_4F_kBogada2VoPGoPlK1kzYSinsrRle5Yv0T9KqmWTG1itMcGD5kJJcsyEkcMdOywSmu3ige3nirHKmEeA\n" +
            "dcMfzdkGeT_1Suh4WlxX5a3fIkkG1almmOOug7wVGt2nNl6FJj5MZU5Uazp5_JyYwZgyNk69SKdSx8fOjtl9rlrQ\n" +
            "dc8Im6galoSDTHmvzNFaHUvJGSY4r3z2IBRhLbKBN8qpdKfU00Zd9RhbqWXe-JwudMK1bR721LkSYnNjOUDNvQYg\n" +
            "dc2SE-B0pYWXnZhPJ3QVv6zlpNrgvYPp1avfdpnPRstFpa7D_Kv1VjVe4j1owrK4jfaChLjWfbrwUiCr7337tXWA\n" +
            "dcOeEKIB7201P0ynF2henDTjhnfGu-FJ3gaYD3-iCqd26w4ypK5j7fggB6ezsFHcLIgiSFQrKMTZszRXiJAu09xg\n" +
            "dcJm1ze8awlKqoNcdYCM5ILoakSz2Ga1jW6wRmK-7eqvEI0GcziviDY0QxGFbYATn4hAEA8UKbnCfUvvgB3fUQzg\n" +
            "dcOsd_Ht72dQdhAJnWu9JpiinDSmWFDvzoq6xqcshSCP7rTkSdQoAKPk6xOthspfcj9rpW-h8AlidfMXss3d5VOw\n" +
            "dcposbEFhYMTngcnUQ6DoIKESrG5dOarmqzYeQ3awYhlE2vS8BkOB3rb9pJgYtWfx3FOUvbNh-BkZbVFAioU28ew\n" +
            "dcmT2OzEqxUN9k1rnjrzFUGolgswei1HmUlB58nk6llM2mhXpkCfJ6J5dkKMebuADMR0T1DD_bDwsXGKOezFb-pA\n" +
            "dcGr3cM8MxwkDQoLglmj06qrqHYscR0xsD8_FeZMChSDHLF9UEWVF8plRlnTf9Ad_xHoyA4evHB-fqYlPk-h48iw\n" +
            "dcoNKhBsCXXgzeH3VFeMgBMzDSGsUlg1ibzEjWUyhjXOlaJqcv5J6E_JpqGz745Wkh25GvQn6qxo4g3U8jPcTajg\n" +
            "dcxTKvNj1EZOtvP6Jz2Aa9NhDYbsiBmr0McX4MT_UPnXJQjHhqsQb5O1q5ws_B6OVpmEQ1uZZWUOInNl2YlZt01A\n" +
            "dccjRUGaa2Y47VjZ9GNUj4Y7Vfbn0kVgWf8ikBrb3z6Bn4Kz4q2yGKy9RgDs2HA4rFL0kS2KOy-2CCQPmeJJB3lw\n" +
            "dcQJJ2qxMaC0_8NvPnSZOgXd5D0BnODRZ0h29jevmUF2ldIqjniLvIvYnPgOEcm3I6wEMJs5NwmVyP8QKyHuMmFQ\n" +
            "dcEBMFQY_2GfqK5ZLpjW-ktVGMvTTmhwy_z7hzAS5pH4bkQNuz91vENus42hFZHYNSwigW9qwYhOdGwzoyGoIY2Q\n" +
            "dc-EC8pwtLb0nV783XZ18phjb8wByneVvuU8gTBcjDsDuwPCfvtbgGCDtdR9hRcwNFMmFhDX6WLBz0pGjLuu0s4A\n" +
            "dcnIedGSjM8cT5gYn8pmmv6D7LgaervKsVUgse8cd_R6dvufhUhV7nDCvspTPjhw6K5uNMjTDjQq4MmsCjjpeI4A\n" +
            "dcILIClUCaAxXZq9D1V-R-kot7fi9UW-1f2qlZ9GGHecinBHVMhohakmtZOWtLPrOhItlS8M3DKcymFej5oirckA\n" +
            "dcdA-lFFbY2cWNThnjRYf5Uqfo8Tu30BN3d0F0CFBd9TiLGupx_LYJ2NubEYQqicpDwjTrhm-98vQvKS9Pfot3aA\n" +
            "dc5bTUcJ4tsgNdT9-l8M8mc_RtguWOE6_f8U_38g4hearbacCty-CK_ink9gej0NadyjTjXn0a4yvaaWBlce2ovw\n" +
            "dcbLdXMgOP6rs9A-IzAWD2fCCXdvfRkCGRz8tRD0fa-oPxeomG64jnpNFSPGaQPvRzauPCqj-k6iSR5lKWvKEFnw\n" +
            "dck8QwseDlgWsfqqig-XSGy1Qo_OXtjLsU0r3PMD8UoI-FXUipao7wA_ddFfeebZFLpLwxL5_iE_3fizt6G55sbA\n" +
            "dcbGSWOT6OZbk6mGsUc0OFB1NHvm7o9UGIyxt1lJiajvCJXKgQed6XwP8UmOfuCTkdFZDg79ZjTkcVPlZgKgkneg\n" +
            "dc4bQI4oNiLAVCggQbDgEV5_VxEk3MNJMm5FGn_bRKSNTWRnvrTMjRRebRT-XgQEw7ase80RHG6nd6rMt3DKLzHA\n" +
            "dcPe51xA7lTJo_sTITmSKHFiie4NQHpXtngol_h6Cm6BtRCxm7cFNuj6L9yrfv4Rj8mkt2LMFDBu_km9Z6Jmk4pQ\n" +
            "dct6LGjylZUtG9ZISLf50NVP0fcFA0dz-jEGw2JIv_AFMWQGppqNfKPZcKO7F1SGQqznS2s3CbFCdcGRRtJCbi5w\n" +
            "dciblLh6ipS84VSHSgb4ZLgzKUTM5spj-mplvXH8HWMvcrXuSZbn3KLbkWoi5hIMx43R4g3KUechLFXCA1sdhqtQ\n" +
            "dcYP94jpIFiECOz8lLFsd5IqXvTLOgNenkwOZWDGmZZYLyVebS8e1IKnsTG32lnjFxcIuKozVZnJ3VBsFgborvpg\n" +
            "dcT-34hMXjb52d0TTrZyjO3orANoCDyacTQn3pCsqv8_-XzZq-Sa9Pf2AahaZU_8PaMtKC2oymfRyLFW1ed3gAMg\n" +
            "dctYJN62tbsXW8C9ltZ8KzgNw0IqMbJXTF1qz5uYox70frbFM2E5fAKTItVgasWN7R1YiiC5XwsQcG5FLdvCXKSQ\n" +
            "dcW7UicGVfh828i-UxVzrul6k3pUReFcWC0f_azXN3Qzu5NQ9bygdjhcB9emBvCCsSu3hhB2oYSR28UdYf9llrMw\n" +
            "dcnGqTfc-gOzHxf2op8bjD878v-63n1B21t_DOGgbNwfmsEYfGfVt4kXPzCXO075TzZcTbDClgXSjzJYpWZcWGXQ\n" +
            "dcIfZ3RcHh0rvVwHYvjco4jRAcoLpqU2erukUMEdyndKXMO14aCnZccx4IViPKociLXZfQ1EAtV2xph0LS7UuLVg\n" +
            "dcN5wcgoQjhzA1AQMC1CwZUN32LQBb9zIcUZKNuytewx79Ay91uoMwRrjVSF4kn8MAfejRPw4DHuF77f78pZhtNQ\n" +
            "dcpTNJXJZqLpaIUsUMPQzKgpRSECCAJoeZJ-C78PH184wqvWe1sisYOVwuLkNBwfZWWIPxAWipmdu3VJVJ29q_lA\n" +
            "dcuKeXmLrmfcXZ8oOUhR7swMcG3eXVk-cz37D6MeflcJo8PZV6aY6IlVtFD1MnX4DtJgC18YeQ898KCopAYAum2w\n" +
            "dchv62sfLLZdqIXJVIyWRs9MlP1PaodQcSJQFF4tL4Yfa-UZiv4QPugEeAc52cPquv6J8f4ydx-CvgrsgT8-ThuQ\n" +
            "dcM0kWYmug-iE-HlOaeKiBIzhRToeMO5PcZwPhh_IizHp3Ft1VSdjwdw28xHbgSuHen7gusD82nyuPM1EZPO-faw\n" +
            "dchnmu6-s4GZTY-i17Ajf8H0Tkxt9nM-kZwps0hqSnhDrWIfjZ4ILJ7WuFCYSc8bNrezR5HHe2vOnS5zmlNvHrUQ\n" +
            "dcWfIxMHFv0LgGWG25G9SjiFEXVWUD74aGbbkc8-LBppOllGmh4yWf3xwqgPt_nFq8SOstmGdOBAmJd48QdFQ9AQ\n" +
            "dcf7J2P7skceQFgGKolyI1o5_6pu0wBSZWzwXQjix_2XZOdaRyoK8b4cMkvAVs1Mo79LF0fey5rZPYNCCHRPXsag\n" +
            "dcr1WdVadPLvqDZ5umAYefPg4udiIK_ot2bABlsNz9EdvuP05dagzb4zQflXPq2dSEXRyLReOu8IxiY9ZRikwbDQ\n" +
            "dcxpAMMClPCMjfy68GGTdoiLTNb6sXr02YfYeGwjv8YPVfi0uOjXtoaZQwV1DEA82u7SP9SRu__Nd_yN33wEachg\n" +
            "dcu0H2lfR0b7SFOyF73Ae__EU_TmyRaq1rUWVGjKDp4q29cjNqkyyK78dCryGMKx7kM6kYl1YRWwthux5cho3JUw\n" +
            "dcHfz-Nal6dDBc4a9TnFMxH8EEISWY7r7JQrSRrSAP4HVxGX8OHqxjhNvwX6yrLkmd7m1zvNZwTVVr56lJsaz54g\n" +
            "dcJJvLY12i_UpBctZYyxdBdUq6tfX2AqbtqQWhIwT4Gcqtk6s7FcfWtfhnWDEI11BqzmKlNvt1pHC_BdnvfPTijQ\n" +
            "dc-Ij0Q7IBGVXFmRHOmC7WemnhKuuQth0FKVvDdv-iaKgAbcaEEEB2OYv3go8pzzDcEjK41nK910L9A9sYA2FjaA\n" +
            "dcIN2fV796EAHKHPaMizEh-Zj5PPm2IKaBMX4kbxGLliidjM9ybKC0x3tZ6Mfw830mvSesb3izoZnnMfHAbIfpng\n" +
            "dcHAcWA2qwvgmhnjm30l4O0op0hgBBTkce9Ih_Pogg_s4KH8leWy6XayvRFxVJnMbFXXTTjQf8EVebvxcTrsa9PA\n" +
            "dcygAzssC-x2I1ozb7QDXL1SX0yZI1ji6pAhcP533rpepXkc-fQpewOeU4bIXGr91OrRqAq1Pd14pvVnttSVT0HA\n" +
            "dcFI6Mbqg-a2r4wd7zmKnnypqnGKMTklm0G90At4bBj3zri7hYXvXPdAzrC6utDQfk7ccWHI3MqkQTcvEE0fnsZQ\n" +
            "dcOQIGwt__8EonxAJeZPEECK2mer_6AQ6RJtZethWIoRH_AMhvnD1S0lpuFcpTJI81lfZf_cVAXq8Vi-H5ge2ndg\n" +
            "dcTFOVCPp192bq_sh659LvHfqzPQHbvSkTEs7Y5R4UPTj1-0vA1iqYxs3J30NNlW9E78MoP92GlA87afk8OKEd0A\n" +
            "dcaY4JlB2ugQ2hnd8MRkgCwjVfYoZBb8lhn2d2nXIOCx8fZ1jl93fjrR0rvwQHqeJFlIOiwHjLZJTfTSAF7uUhSA\n" +
            "dcsuXyX0dvYcFuOuj6Ya_I_cnvlTFDXPVAF4S4yOxbFMBIcKDKHBlauiwVQ_q4MOfWO9MztglTFm71jn7OmAuEsQ\n" +
            "dcAw6g4meD0EqplyBsNtjc_3TCTgVmRTxeb7fo_9CkowyKzuav6y5zImpU5k6IjMA40xZUSt_HCG60_XWS2qIT2Q\n" +
            "dclUfZTSFb4Rnqfqz45fv1KuBUbsoRdjUMQtFJ9ELVQzRF8VxIFZNiHJt8DQAozRI3mupNuF0Lp-JCLrR3mR07Sw\n" +
            "dcbgsG1GYvPEoVTez9XQdpZkZot_f3JMEg2mxwBuCVPwM2etqktPL3APrUlw2B7N8geAkeF17zMwxLXOWeu7cJ0w\n" +
            "dc6wWAqsd2gKba8aC3903wwW3tp9SeS6cKhmoElu_0-iCxOkkEKxQJKwtN0J9je39OLzmWex5-WNnNCT4IXsS0WA\n" +
            "dcurURakjpEWpvWzs3NL0KelJ6UCo-ZWScA3vZ6qGu8cF8Nu4iWaC1-WaPomY32EvDOE2rdm-YiMk1ZgRXtQJWPQ\n" +
            "dcO6gXzf2YLN7GWexx_zpPVEfntFE1wvGc-o5uKcW32GhN6V4MkMecNUKrr_MV7EYcMimy7G-pnlbE6XEQGPs9Tw\n" +
            "dczhexPlqEU_88SmnjBIiynTbH_zpyFbGB3b9AakxXaoDVYsix6D6UlclR9jW2AKaIfVQ_kke-KgHo0otnzbzV6A\n" +
            "dcaD2SF7gLZ72YHIAvDyxFM_t9X1_c6S33XLBeNbjvAGwMzWzJ9aX5fT3F7zbjNBkWDZout6BJizqo4sBA1_6o7Q\n" +
            "dcsYNtNyNZTayI0VJ8BUGzNNRBk-3NhT9zaNa3Myy9cbp4R9PQOvC-vyMRCSzV44pH1y6QE0wt1f6U4kSnm7mkNg\n" +
            "dc6913Z3m84vbbHiNjnn_7v7jzR3tdDB02jEliFLr_0N03AejlP-jcAiZBKVROw_VcIbCseEOsxTHgUhXyrWHkzA\n" +
            "dcatFfD85HhSuRANIXwZTwy6aEEaAcbkK4hkRejmqsXiu3IYtdfLmOcLl1oli_yohkMBTghtC6tOB4QGlnIf884w\n" +
            "dcytDUHFQn-ynTGV_iY9E4tEzW1nq8u4mZdqlE3J101AkJF8nBDdVrAWPModet1UlE33988tdoMS1w5_U8FGU0ZA\n" +
            "dc5CDP3sYNRXfxx9Y-BT28a8n_-qBWw-C2Sm1MyTxCQpvw3ozdGyeNfEkpF5VtnDFZMaYE8JXNZhg8P_n9cvfRmQ\n" +
            "dcil8WC_Hk0tw6_GAgOhIJR_Pc5VWCnCWoRPwwoXPURUa0Iwl9Of_C3EjUygbngO-4AOBRC11UtSal8A_bNboXDw\n" +
            "dcxPHCB-zcALBl_1GaHBmLSAx7vl59KEdsK2b-rhp-cNWWC88Lr2l4kvoz3_Y535kly6tYRQqb7sYwi1lvsFnZCA\n" +
            "dcqgDlVFUMsf2RAXLNVUDi9u0vlDo0utSiupEfRaegLsdRHl3OaVOb1AdqYokvRxiL_CwB5B_S-jHFo8SbCGSxqA\n" +
            "dclg3xyl48YTU3XLftfAiHtV3uA_uF-zo6bXpPvNj4lgQEXiph9EvyrglGZdxnafr89PjOphRlwHXnjgZzs-FCWg\n" +
            "dcy7mKvzHm1y6oEOTz1zywEEyFwZipmE-0bSsptrS7zRFNe0hCRNAm04FXhccc6U1kkdnjN_RKQxy-v4irL3nIfg\n" +
            "dcUb-5tQ1jyM-WOhBabMUnOaFFE6fuxYDlmoT5VsDmTH04rllUF5pX3x1Soml59mrfGeUUawKGl_sk2Ow325Vbfg\n" +
            "dcQtW1Qjdlr9ASQyj87Ts8yqxAccldUp4RPyrRQ9Ote5UdRz4wV3LjGyNRkAtli7fHYQOb1TeOqPx0vjkJX-6yPA\n" +
            "dc70jObi6BdZQU92NqQHLwNaesKJ1CqmxcsFyxxGnTxPKapVGmhRz00JVxzIHxLVFXT-zzhhofGYtYHvpHt305gw\n" +
            "dcPfTeYSnvC5hCLEW-ZLWQ2zGXwjImacoC6y9B-T6IG2mAKKyzf4nlYfkKhmRizx1lxMWkKVjGI5VquC724Ac85w\n" +
            "dcnynBkswba0f8QkSgPYs0A0w1DTfB80FnJjliAXbliwUE2iYfcQcw_84SCJjmYfA7s1vqV9fHWR1qsjj5X_wTkw\n";

    public static void main(String[] args) {
    }

    @Test
    public void deleteDoc() {
        RestTemplate restTemplate = new RestTemplate();
        String[] split = aa.split("\n");
        for (String docId : split) {
            Request request = new Request();
            request.setDocid(docId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);
            String s = restTemplate.postForObject("https://qyapi.weixin.qq.com/cgi-bin/wedoc/del_doc?access_token=E2FWA1acdtAsxMFIT_YuXmM-pP_v3Ja3azSrCKhipZR33MN9Fu7ZAkgqygEuKiujDCy9nIMbo9eCxqDHTIXjPMTqanRCX4TJQsU3qPoQldp7UyAI-mII8pAvHq9dQcYr77Fl2YXqgHyZWa-sdKiiBL9Sm1SKEFkFGw8Qy0bZnQ4mHKDZUqv564CPTJ5rdWu7UI62zBKig76FhOZ8wIDLTw",
                    request, String.class);
            System.out.println(s);
        }
    }

    public static class Request {
        private String docid;

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }
    }

}
