--- Location in GIMS DB for information in the json version of 
--- the sample sheet. 

--import_file    CFSAN00101_09_20200102_1216.trimmed.fastq.tgz
select top 10 * from attachmentlink  order by 1 desc
select top 10 * from attachment where attm_pk = 3400931 
select top 10 * from location where lctn_pk = 167

--library_id     LIB_TEST
select top 140 * from tablefield  
where tbfl_name like '%kit%'
select top 140 * from tablefieldvalue
where tbfv_fk_tablefield = 133

--dna kit        TEST_DNAKIT
select top 140 * from tablefield  
where tbfl_name like '%dna%'

--sequencing_kit  SQK-RBK004
cntn_cf_sequenceChemistty
select top 2 * from tablefield  
where tbfl_name like '%sequenceChemistty%'

--flowcell    FAK80437 
    -- xprm_cf_flowCellID
select top 4 * from tablefield  
where tbfl_name like '%cell%'

--sequencer   RogueOne
    -- cntn_cf_sequenceMachineId
    -- nstr_cf_machineId
select top 4 * from tablefield  
where tbfl_name like '%machine%'

--run_month   11
--run_year    2019
--run_id      priceless_wing
    --cntn_cf_sequenceRunDate
    --cntn_cf_runCell
    --cntn_cf_RunID
    --cntn_cf_nextseqRunId
select top 4 * from tablefield  
where tbfl_name like '%run%'

--sample_id   SAMPLE_TEST_01
select top 1 * from contenttype   
where cntp_name like '%sample%'
--barcode_id  09
select top 1 cntn_id, cntn_barCode from content 
where cntn_fk_contentType = (select top 1 cntp_pk from contenttype where cntp_name like '%sample%')

--accession   CFSAN00101
select top 4 * from tablefield  
where tbfl_name like '%accession%'
select top 10 * from content cn
join TablefieldValue tv1 on tv1.tbfv_fk_content = cn.cntn_pk and tv1.tbfv_fk_tablefield in (97) 
where 1=1
--and cntn_id like 'LIB%'
order by tv1.tbfv_pk desc 

--organism         Pseudomonas aeruginosa
select top 4 * from tablefield  
where tbfl_name like '%organism%'

--extraction_kit   TEST_KIT_KIT

--comment          If a comment were to be made this is what it would look like
select top 4 * from tablefield  
where tbfl_name like '%comment%'

--user             veet.voojagig@fda.hhs.gov
select top 4 * from tablefield  
where tbfl_name like '%user%'